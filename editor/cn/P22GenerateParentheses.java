//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
//
// Related Topics 字符串 动态规划 回溯 👍 4013 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

//Java：括号生成
public class P22GenerateParentheses {
    public static void main(String[] args) {
        Solution solution = new P22GenerateParentheses().new Solution();
        solution.generateParenthesis(3);
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            backtrack(n, res, new StringBuilder(), 0, 0);
            return res;
        }

        private void backtrack(int n, List<String> res, StringBuilder stringBuilder, int l, int r) {
            if (l == n && r == n) {
                res.add(stringBuilder.toString());
                return;
            }
            if (l < r || l > n) {
                return;
            } else {
                stringBuilder.append('(');
                backtrack(n, res, stringBuilder, l + 1, r);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                stringBuilder.append(')');
                backtrack(n, res, stringBuilder, l, r + 1);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
