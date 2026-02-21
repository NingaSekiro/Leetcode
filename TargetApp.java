package leetcode;

// File: TargetApp.java
public class TargetApp {

    public static void main(String[] args) throws InterruptedException {
        int counter = 0;
        System.out.println("目标程序 TargetApp 已启动...");

        while (true) {
            System.out.println("循环计数: " + counter); // 我们将在此处设置断点
            counter++;
            Thread.sleep(2000);
        }
    }

    /**
     * 这是一个静态方法，我们将从 JDI 客户端远程调用它。
     * @param name 从JDI客户端传来的名字
     */
    public static void sayHelloFromJdi(String name) {
        System.out.println("=============================================");
        System.out.println("[远程调用成功] 来自 " + name + " 的问候！");
        System.out.println("Hello, JDI World!");
        System.out.println("=============================================");
    }
}