package leetcode.editor.cn;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ddSolution {
    public static void main(String[] args) throws Exception {
    }

    public boolean isSimilarString(String subStr, String word) {
        int index = 0;
        int r = 0;
        int length = word.length();
        while (index < subStr.length() && r < length) {
            if (subStr.charAt(index) == word.charAt(r)) {
                index++;
                r++;
            } else {
                r++;
            }
        }
        if (index != subStr.length()) {
            return false;
        }
        return 10 * subStr.length() - 7 * length > 0;
    }
}

class Calculator {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int sub(int a, int b) {
        return a - b;
    }

    public static int mul(int a, int b) {
        return a * b;
    }

    public static int div(int a, int b) {
        return a / b;
    }
}