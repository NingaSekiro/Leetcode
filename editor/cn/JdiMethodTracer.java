package leetcode.editor.cn;

import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.tools.jdi.ArrayReferenceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class JdiMethodTracer {
    private static VirtualMachine targetVirtualMachine;
    private static final String TARGET_CLASS = "org.example.controller.BeanController";
    private static final int BREAKPOINT_LINE = 42; // TargetApp.java 的第11行: System.out.println("循环计数: " + counter);


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
        targetVirtualMachine = vm;
        EventRequestManager erm = vm.eventRequestManager();
        // b. 找到目标类 (注意：此时目标类的 main 可能还没执行，但类已被加载)
        Thread.sleep(5000);
        List<ReferenceType> refTypes = vm.classesByName(TARGET_CLASS);
        if (refTypes.isEmpty()) {
            System.err.println("错误：找不到类 " + TARGET_CLASS);
            return;
        }
        ClassType targetClass = (ClassType) refTypes.get(0);

        // c. 在指定行的第一个位置创建断点
//        Location location = targetClass.locationsOfLine(BREAKPOINT_LINE).get(0);
//        BreakpointRequest breakpointRequest = erm.createBreakpointRequest(location);
//        breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD); // 只挂起当前线程
//        breakpointRequest.enable();

        MethodEntryRequest methodEntryRequest = methodEntryRequest();
        methodEntryRequest.enable();

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
                } else if (event instanceof MethodEntryEvent) {
                    methodEntryRequest.disable();
                    vm.eventRequestManager().deleteEventRequest(methodEntryRequest);
                    ThreadReference thread = ((MethodEntryEvent) event).thread();
                    String inputJarPath = "D:\\Tmp\\aopbuddy-1.0-jar-with-dependencies.jar";

//                    List<ReferenceType> refTypes = vm.classesByName(TARGET_CLASS);

                    byte[] jarBytes = Files.readAllBytes(Paths.get(inputJarPath));
                    ArrayReference byteArrayMirror = toByteArrayMirror(vm, jarBytes);
//                    List<Method> methods = targetClass.methodsByName("sayHelloFromJdi");
//                    Method remoteMethod = methods.get(0); // 假设没有重载
//                    StringReference nameArg = vm.mirrorOf("JDI Client");
//                    targetClass.invokeMethod(thread, remoteMethod,
//                            Collections.singletonList(nameArg), ClassType.INVOKE_SINGLE_THREADED);

                    ClassType classType2;
//
//                    Method method2 = (classType2 = (ClassType) targetVirtualMachine.classesByName("java.lang.ClassLoader").get(0)).methodsByName("getSystemClassLoader", "()Ljava/lang/ClassLoader;").get(0);
//
//                    ClassLoaderReference classLoaderReference;
//                    classLoaderReference = (ClassLoaderReference) classType2.invokeMethod(thread, method2,
//                            Collections.emptyList(), 1);
                    ClassType filesClass = (ClassType) vm.classesByName("java.nio.file.Files").get(0);
                    // 获取java.nio.file.Paths类
                    ClassType pathsClass = (ClassType) vm.classesByName("java.nio.file.Paths").get(0);

                    // 获取Paths.get(String)方法
                    Method pathsGetMethod = pathsClass.methodsByName("get").stream()
                            .filter(m -> m.signature().equals("(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;"))
                            .findFirst()
                            .get();

                    // 获取Files.write(Path, byte[])方法
                    Method filesWriteMethod = filesClass.methodsByName("write").stream()
                            .filter(m -> m.signature().equals("(Ljava/nio/file/Path;[B[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;"))
                            .findFirst()
                            .get();

                    // 准备参数
                    StringReference outputPathArg = vm.mirrorOf("D:\\Code\\leetcode\\leetcode\\editor\\cn\\output.jar");

                    // 创建ByteArrayReference
//                    ArrayReference jarBytesArg = vm.mirrorOf(jarBytesArray);

                    // 调用Paths.get(outputJarPath)
                    Value pathValue = pathsClass.invokeMethod(thread, pathsGetMethod,
                            Collections.singletonList(outputPathArg), ClassType.INVOKE_SINGLE_THREADED);

                    // 调用Files.write(Paths.get(outputJarPath), jarBytes)
                    Value writeResult = filesClass.invokeMethod(thread, filesWriteMethod,
                            Arrays.asList(pathValue, byteArrayMirror), ClassType.INVOKE_SINGLE_THREADED);
                    System.out.println("dddddd");
// ... existing code ...
                }
                // 恢复VM的执行
                eventSet.resume();
            }
        }
    }

    public static ArrayReference toByteArrayMirror(VirtualMachine virtualMachine, byte[] bytes) throws Exception {
        List<ReferenceType> refs = virtualMachine.classesByName("byte[]");
        ArrayType arrayType = (ArrayType) refs.get(0);

        ArrayReference array = arrayType.newInstance(bytes.length);

        List<Value> mirrors = new ArrayList<>(bytes.length);
        for (byte b : bytes) {
            mirrors.add(virtualMachine.mirrorOf(b));
        }
        array.setValues(mirrors);
        return array;
    }


    private static MethodEntryRequest methodEntryRequest() {
        MethodEntryRequest methodEntryRequest;
        (methodEntryRequest = targetVirtualMachine.eventRequestManager().createMethodEntryRequest()).addClassExclusionFilter("java.lang.*");
        methodEntryRequest.addClassExclusionFilter("sun.misc.*");
        methodEntryRequest.addClassExclusionFilter("com.adapgpt.*");
        methodEntryRequest.addClassExclusionFilter("org.adapgpt.*");
        methodEntryRequest.setSuspendPolicy(1);
        return methodEntryRequest;
    }

    private static String encodeJarToBase64(String jarPath) throws IOException {
        // 读取jar包文件内容
        byte[] jarBytes = Files.readAllBytes(Paths.get(jarPath));
        // 进行Base64编码
        return Base64.getEncoder().encodeToString(jarBytes);
    }

    private static void decodeBase64ToJar(String base64Jar, String outputJarPath) throws IOException {
        // 将Base64编码的字符串解码为字节数组
        byte[] jarBytes = Base64.getDecoder().decode(base64Jar);
        // 将字节数组写入到新的jar包文件
        Files.write(Paths.get(outputJarPath), jarBytes);
    }
}
