//给你一个字符串 s，找到 s 中最长的 回文 子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母组成 
// 
//
// Related Topics 双指针 字符串 动态规划 👍 7787 👎 0


package leetcode.editor.cn;

//Java：最长回文子串
public class P5LongestPalindromicSubstring {
    public static void main(String[] args) {
        Solution solution = new P5LongestPalindromicSubstring().new Solution();
        solution.longestPalindrome("bb");
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String longestPalindrome(String s) {
            int n = s.length();
            if (n < 2) return s;

            boolean[][] dp = new boolean[n][n];

            int start = 0;   // 最长回文起点
            int maxLen = 1;  // 最长回文长度

            // 枚举右端点 j
            for (int j = 0; j < n; j++) {
                // 枚举左端点 i
                for (int i = 0; i <= j; i++) {

                    // 判断 dp[i][j]
                    if (s.charAt(i) == s.charAt(j)) {
                        //左右端点之间的数字个数小于等于1或者中间本身就是回文数
                        if (j - i <= 2 || dp[i + 1][j - 1]) {
                            dp[i][j] = true;

                            int len = j - i + 1;
                            //更新最长回文长度和起点
                            if (len > maxLen) {
                                maxLen = len;
                                start = i;
                            }
                        }
                    }
                }
            }
            return s.substring(start, start + maxLen);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
