package leetcode;// File: JdiClient.java
import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JdiClient {

    private static final String TARGET_CLASS = "leetcode.TargetApp";
    private static final int BREAKPOINT_LINE = 11; // TargetApp.java 的第11行: System.out.println("循环计数: " + counter);

    public static void main(String[] args) throws Exception {
        // 1. 查找并获取一个“启动连接器”
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager().defaultConnector();

        // 2. 获取连接器的默认参数，并设置我们要启动的主类
        Map<String, Connector.Argument> arguments = launchingConnector.defaultArguments();
        arguments.get("main").setValue(TARGET_CLASS);


        // 设置类路径，确保目标JVM能找到TargetApp类
        String classpath = "out" + File.separator + "production" + File.separator + "leetcode";
        if (arguments.containsKey("options")) {
            arguments.get("options").setValue("-cp " + classpath);
        }


        System.out.println("JDI 客户端：即将以调试模式启动 " + TARGET_CLASS);

        // 3. 使用配置好的连接器启动目标 JVM
        VirtualMachine vm = launchingConnector.launch(arguments);
        System.out.println("JDI 客户端：已连接到目标 VM: " + vm.description());

        // 4. 创建一个断点请求
        // a. 获取 EventRequestManager
        EventRequestManager erm = vm.eventRequestManager();
        // b. 找到目标类 (注意：此时目标类的 main 可能还没执行，但类已被加载)
        List<ReferenceType> refTypes = vm.classesByName(TARGET_CLASS);
        if (refTypes.isEmpty()) {
            System.err.println("错误：找不到类 " + TARGET_CLASS);
            return;
        }
        ClassType targetClass = (ClassType) refTypes.get(0);
        
        // c. 在指定行的第一个位置创建断点
        Location location = targetClass.locationsOfLine(BREAKPOINT_LINE).get(0);
        BreakpointRequest breakpointRequest = erm.createBreakpointRequest(location);
        breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD); // 只挂起当前线程
        breakpointRequest.enable();
        System.out.println("JDI 客户端：已在 " + TARGET_CLASS + ":" + BREAKPOINT_LINE + " 设置断点。");

        // 5. 进入事件处理循环
        EventQueue eventQueue = vm.eventQueue();
        boolean connected = true;
        boolean methodInvoked = false;

        System.out.println("JDI 客户端：等待事件...");
        while (connected) {
            EventSet eventSet = eventQueue.remove(); // 阻塞等待事件
            for (Event event : eventSet) {
                if (event instanceof VMDisconnectEvent) {
                    // VM 断开连接事件
                    connected = false;
                    System.out.println("JDI 客户端：目标 VM 已断开连接。");
                    
                } else if (event instanceof BreakpointEvent) {
                    // 断点命中事件
                    System.out.println("\nJDI 客户端：断点命中！");
                    BreakpointEvent bpEvent = (BreakpointEvent) event;
                    ThreadReference suspendedThread = bpEvent.thread(); // 获取被挂起的线程

                    if (!methodInvoked) {
                        System.out.println("JDI 客户端：准备调用目标方法...");
                        
                        // a. 找到要调用的方法: sayHelloFromJdi(String)
                        List<Method> methods = targetClass.methodsByName("sayHelloFromJdi");
                        Method remoteMethod = methods.get(0); // 假设没有重载

                        // b. 准备参数，需要将Java值“镜像”为VM中的值
                        StringReference nameArg = vm.mirrorOf("JDI Client");

                        // c. 调用静态方法！
                        // 参数: (挂起的线程, 方法, 参数列表, 调用选项)
                        System.out.println("JDI 客户端：正在执行 invokeMethod...");
                        targetClass.invokeMethod(suspendedThread, remoteMethod,
                                Collections.singletonList(nameArg), ClassType.INVOKE_SINGLE_THREADED);

                        System.out.println("JDI 客户端：方法调用完成！");
                        methodInvoked = true; // 演示目的，只调用一次
                    } else {
                         System.out.println("JDI 客户端：方法已调用过，本次仅恢复程序。");
                    }
                }
                // 恢复VM的执行
                eventSet.resume();
            }
        }
    }
}