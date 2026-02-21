package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Demo {
    private static final Logger LOGGER = Logger.getLogger(Demo.class.getName());

    private static List<List<String>> res = new ArrayList<>();
    private static Map<Character, String> map = new HashMap<>();

    public static void main(String[] args) {
        //请在此处完成程序并将结果以System.out.println()输出
        LOGGER.log(java.util.logging.Level.WARNING, "This is an info message",new Throwable());
        map.put('2', "abc");
        map.put('3', "def");
        String digits = "23";
        backtrack(digits, new ArrayList<>(), 0);
        System.out.println(res);
    }

    public static void backtrack(String digits, List tmp, int index) {
        if (tmp.size() == digits.length()) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        String s = map.get(digits.charAt(index));
        for (int j = 0; j < s.length(); j++) {
            tmp.add(s.charAt(j));
            backtrack(digits, tmp, index + 1);
            tmp.remove(tmp.size() - 1);
        }
    }
}
