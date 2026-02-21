package leetcode.editor.cn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 演示将jar包进行Base64编码后再解码成另一个jar包
 */
public class JarBase64Demo {
    
    public static void main(String[] args) {
        try {
            // 输入jar包路径
            String inputJarPath = "D:\\Code\\leetcode\\leetcode\\editor\\cn\\aopbuddy-1.0-jar-with-dependencies.jar";
            // 输出jar包路径
            String outputJarPath = "D:\\Code\\leetcode\\leetcode\\editor\\cn\\aopbuddy-decoded.jar";
            
            // 1. 读取jar包文件并进行Base64编码
            String base64Jar = encodeJarToBase64(inputJarPath);
            System.out.println("Base64编码完成，编码后的字符串长度: " + base64Jar.length());
            
            // 2. 将Base64编码的字符串解码并保存为新的jar包
            decodeBase64ToJar(base64Jar, outputJarPath);
            System.out.println("Base64解码完成，已保存为: " + outputJarPath);
            
            // 3. 验证两个jar包的大小是否一致
            long originalSize = Files.size(Paths.get(inputJarPath));
            long decodedSize = Files.size(Paths.get(outputJarPath));
            System.out.println("原始jar包大小: " + originalSize + " 字节");
            System.out.println("解码后jar包大小: " + decodedSize + " 字节");
            System.out.println("编码解码验证: " + (originalSize == decodedSize ? "成功" : "失败"));
            
        } catch (IOException e) {
            System.err.println("处理过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 将jar包文件转换为Base64编码的字符串
     */
    private static String encodeJarToBase64(String jarPath) throws IOException {
        // 读取jar包文件内容
        byte[] jarBytes = Files.readAllBytes(Paths.get(jarPath));
        // 进行Base64编码
        return Base64.getEncoder().encodeToString(jarBytes);
    }
    
    /**
     * 将Base64编码的字符串解码并保存为jar包文件
     */
    private static void decodeBase64ToJar(String base64Jar, String outputJarPath) throws IOException {
        // 将Base64编码的字符串解码为字节数组
        byte[] jarBytes = Base64.getDecoder().decode(base64Jar);
        // 将字节数组写入到新的jar包文件
        Files.write(Paths.get(outputJarPath), jarBytes);
    }
}