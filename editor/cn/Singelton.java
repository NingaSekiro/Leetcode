package leetcode.editor.cn;

import java.util.concurrent.CountDownLatch;

public class Singelton {
    // volatile 保证可见性
    private static volatile Singelton instance;
    private Singelton() {
    }
    public static Singelton getInstance() {
        // 双重检查
        // 第一次检查,不为空不必加锁
        if (instance == null) {
            synchronized (Singelton.class) {
                // 第二次检查,避免重复new
                if (instance == null) {
                    instance = new Singelton();
                }
            }
        }
        return instance;
    }

}
