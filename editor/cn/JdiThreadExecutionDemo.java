package leetcode.editor.cn;

import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;

import java.util.*;

/**
 * 演示如何仿照xcodemap获取JDI可执行线程，并使用该线程执行Model的test方法
 */
public class JdiThreadExecutionDemo {

    public static void main(String[] args) throws Exception {
        // 连接到远程 JVM (JDWP 已经开启)
        AttachingConnector connector = Bootstrap.virtualMachineManager()
                .attachingConnectors()
                .stream()
                .filter(c -> c.name().equals("com.sun.jdi.SocketAttach"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No SocketAttach connector"));
        Map<String, Connector.Argument> arguments = connector.defaultArguments();
        arguments.get("hostname").setValue("127.0.0.1");
        arguments.get("port").setValue("5005");
        VirtualMachine vm = connector.attach(arguments);

        System.out.println("成功连接到远程JVM");

        try {
            // 仿照xcodemap获取合适的可执行线程
            ThreadReference threadRef = getSuitableThread(vm);
            System.out.println("获取到合适的线程: " + threadRef.name());

            // 执行Model.test方法
            executeModelTest(vm, threadRef);
            System.out.println("成功执行Model.test方法");
        } catch (Exception e) {
            System.err.println("执行过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            vm.dispose();
            System.out.println("已断开与远程JVM的连接");
        }
    }

    /**
     * 参考xcodemap第一次获取合适的可执行线程的方法
     */
    private static ThreadReference getSuitableThread(VirtualMachine vm) {
        // 步骤1: 尝试直接查找名称包含"daemon"的运行中线程
        ThreadReference daemonThread = findDaemonThread(vm);
        if (daemonThread != null) {
            return daemonThread;
        }

        // 步骤2: 如果没有找到守护线程，使用事件监听机制获取线程
        try {
            ThreadReference eventThread = waitForThreadViaEvent(vm);
            if (eventThread != null) {
                return eventThread;
            }
        } catch (Exception e) {
            System.err.println("通过事件监听获取线程失败: " + e.getMessage());
        }

        // 步骤3: 回退策略，使用第一个可用线程
        List<ThreadReference> threads = vm.allThreads();
        if (!threads.isEmpty()) {
            ThreadReference fallbackThread = threads.get(0);
            System.out.println("使用回退线程: " + fallbackThread.name());
            return fallbackThread;
        }

        throw new RuntimeException("没有找到可用线程");
    }

    /**
     * 查找名称包含"daemon"的运行中线程
     */
    private static ThreadReference findDaemonThread(VirtualMachine vm) {
        List<ThreadReference> threads = vm.allThreads();
        for (ThreadReference thread : threads) {
            try {
                if (thread.name().toLowerCase().contains("daemon") &&
                        thread.status() == ThreadReference.THREAD_STATUS_RUNNING) {
                    System.out.println("找到守护线程: " + thread.name());

                    // 参考xcodemap，对选中的线程执行挂起操作
                    thread.suspend();
                    try {
                        // 短暂挂起后恢复，确保线程状态稳定
                        thread.resume();
                    } catch (Exception e) {
                        System.err.println("恢复线程时出错: " + e.getMessage());
                    }

                    return thread;
                }
            } catch (Exception e) {
                System.err.println("检查线程时出错: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 通过事件监听方式获取线程
     * 参考xcodemap的MethodEntryRequest机制
     */
    private static ThreadReference waitForThreadViaEvent(VirtualMachine vm) throws Exception {
        final ThreadReference[] foundThread = new ThreadReference[1];

        // 创建事件队列和事件管理器
        EventQueue eventQueue = vm.eventQueue();
        EventRequestManager erm = vm.eventRequestManager();

        // 创建方法进入事件请求
        MethodEntryRequest methodEntryRequest = erm.createMethodEntryRequest();
        methodEntryRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE); // 不挂起线程

        // 监听常见的系统类，增加捕获事件的概率
        List<ReferenceType> systemClasses = vm.classesByName("java.lang.Object");
        if (!systemClasses.isEmpty()) {
            methodEntryRequest.addClassFilter(systemClasses.get(0));
        }

        // 启用事件请求
        methodEntryRequest.enable();

        System.out.println("开始监听线程事件...");

        try {
            // 设置超时时间（5秒）
            long startTime = System.currentTimeMillis();
            long timeout = 5000; // 5秒

            while (foundThread[0] == null && (System.currentTimeMillis() - startTime) < timeout) {
                try {
                    // 尝试获取事件，最多等待500毫秒
                    EventSet eventSet = eventQueue.remove(500);
                    if (eventSet != null) {
                        for (Event event : eventSet) {
                            if (event instanceof MethodEntryEvent) {
                                MethodEntryEvent methodEvent = (MethodEntryEvent) event;
                                ThreadReference thread = methodEvent.thread();

                                // 检查线程状态是否有效
                                if (thread.status() == ThreadReference.THREAD_STATUS_RUNNING ||
                                        thread.status() == ThreadReference.THREAD_STATUS_SLEEPING) {
                                    System.out.println("通过事件监听到合适线程: " + thread.name());
                                    foundThread[0] = thread;
                                    break;
                                }
                            }
                            // 处理完事件后继续
                            eventSet.resume();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            // 禁用并清除事件请求
            methodEntryRequest.disable();
            erm.deleteEventRequest(methodEntryRequest);
        }

        return foundThread[0];
    }

    /**
     * 执行Model的test方法
     */
    private static void executeModelTest(VirtualMachine vm, ThreadReference threadRef) throws Exception {
        // 加载Model类
        ReferenceType modelClass = null;
        List<ReferenceType> classes = vm.classesByName("leetcode.editor.cn.Model");

        if (classes.isEmpty()) {
            // 如果Model类尚未加载，则尝试在远程JVM中加载它
            System.out.println("Model类尚未加载，尝试创建实例...");
            modelClass = loadModelClass(vm, threadRef);
        } else {
            modelClass = classes.get(0);
            System.out.println("成功加载Model类");
        }

        // 获取Model的test方法
        List<Method> methods = modelClass.methodsByName("test");
        if (methods.isEmpty()) {
            throw new RuntimeException("未找到Model.test方法");
        }
        Method testMethod = methods.get(0);

        // 创建Model实例（如果需要）
        ObjectReference modelInstance = null;
        if (!testMethod.isStatic()) {
            // 查找已有的Model实例
            List<ObjectReference> instances = ((ClassType)modelClass).instances(0);
            if (instances.isEmpty()) {
                // 如果没有实例，则创建新实例
                modelInstance = createModelInstance(vm, threadRef, (ClassType)modelClass);
            } else {
                modelInstance = instances.get(0);
            }
        }

        // 执行test方法
        System.out.println("准备执行Model.test方法...");
        if (testMethod.isStatic()) {
            // 静态方法调用
            ((ClassType)modelClass).invokeMethod(threadRef, testMethod, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
        } else {
            // 实例方法调用
            modelInstance.invokeMethod(threadRef, testMethod, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
        }
    }

    /**
     * 在远程JVM中加载Model类
     */
    private static ReferenceType loadModelClass(VirtualMachine vm, ThreadReference threadRef) throws Exception {
        // 这里简化处理，实际可能需要通过远程类加载器加载类
        // 在这个demo中，我们假设远程JVM可以访问到Model类
        // 如果需要更复杂的动态类加载，可以参考之前的Base64编码方式
        System.out.println("尝试创建Model实例来触发类加载...");

        // 获取System类
        ReferenceType systemClass = vm.classesByName("java.lang.System").get(0);
        Method getPropertyMethod = systemClass.methodsByName("getProperty", "(Ljava/lang/String;)Ljava/lang/String;").get(0);

        // 获取类路径
        StringReference propName = vm.mirrorOf("java.class.path");
        // ReferenceType需要转换为ClassType才能调用invokeMethod
        Value classPathValue = ((ClassType)systemClass).invokeMethod(threadRef, getPropertyMethod, java.util.Collections.singletonList(propName), ObjectReference.INVOKE_SINGLE_THREADED);
        String classPath = classPathValue.toString();
        System.out.println("远程JVM类路径: " + classPath);

        // 尝试通过反射创建实例来触发类加载
        try {
            // 获取Class类
            ReferenceType classClass = vm.classesByName("java.lang.Class").get(0);
            Method forNameMethod = classClass.methodsByName("forName", "(Ljava/lang/String;)Ljava/lang/Class;").get(0);

            // 调用Class.forName加载Model类
            StringReference className = vm.mirrorOf("leetcode.editor.cn.Model");
            ((ClassType)classClass).invokeMethod(threadRef, forNameMethod, java.util.Collections.singletonList(className), ObjectReference.INVOKE_SINGLE_THREADED);

            // 重新获取加载后的类
            List<ReferenceType> loadedClasses = vm.classesByName("leetcode.editor.cn.Model");
            if (!loadedClasses.isEmpty()) {
                return loadedClasses.get(0);
            }
        } catch (Exception e) {
            System.err.println("尝试加载Model类失败: " + e.getMessage());
        }

        throw new RuntimeException("无法在远程JVM中加载Model类，请确保类路径正确");
    }

    /**
     * 在远程JVM中创建Model实例
     */
    private static ObjectReference createModelInstance(VirtualMachine vm, ThreadReference threadRef, ClassType modelClass) throws Exception {
        // 获取默认构造函数
        Method constructor = modelClass.concreteMethodByName("<init>", "()V");
        if (constructor == null) {
            throw new RuntimeException("未找到Model类的默认构造函数");
        }

        // 创建实例
        ObjectReference instance = modelClass.newInstance(
                threadRef, constructor, Collections.emptyList(), ObjectReference.INVOKE_SINGLE_THREADED);
        System.out.println("成功创建Model实例");
        return instance;
    }
}