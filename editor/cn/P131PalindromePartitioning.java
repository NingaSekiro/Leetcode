//给你一个字符串 s，请你将 s 分割成一些 子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "aab"
//输出：[["a","a","b"],["aa","b"]]
// 
//
// 示例 2： 
//
// 
//输入：s = "a"
//输出：[["a"]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 16 
// s 仅由小写英文字母组成 
// 
//
// Related Topics 字符串 动态规划 回溯 👍 2166 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

//Java：分割回文串
public class P131PalindromePartitioning {
    public static void main(String[] args) {
        Solution solution = new P131PalindromePartitioning().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        List<List<String>> res = new ArrayList<>();

        public List<List<String>> partition(String s) {
            bakctrack(s, 0, 0, new ArrayList<>());
            return res;
        }

        // 面向每个下标的选择
        private void bakctrack(String s, int index, int start, List<String> path) {
            if (index == s.length()) {
                res.add(new ArrayList<>(path));
                return;
            }
            if (index<s.length()-1) {
                bakctrack(s, index + 1, start, path);
            }

            if (isPalindrome(s, start, index)) {
                path.add(s.substring(start, index + 1));
                bakctrack(s, index + 1, index + 1, path);
                path.removeLast();
            }
        }


        // 面向结果
        private void bakctrack(String s, int index, List<String> path) {
            if (index == s.length()) {
                res.add(new ArrayList<>(path));
            }
            for (int i = index; i < s.length(); i++) {
                if (isPalindrome(s, index, i)) {
                    path.add(s.substring(index, i + 1));
                    bakctrack(s, i + 1, path);
                    path.removeLast();
                }
            }
        }

        private boolean isPalindrome(String s, int left, int right) {
            while (left < right) {
                if (s.charAt(left++) != s.charAt(right--)) {
                    return false;
                }
            }
            return true;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}
