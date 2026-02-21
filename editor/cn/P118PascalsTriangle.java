//给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。 
//
// 在「杨辉三角」中，每个数是它左上方和右上方的数的和。 
//
// 
//
// 
//
// 示例 1: 
//
// 
//输入: numRows = 5
//输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
// 
//
// 示例 2: 
//
// 
//输入: numRows = 1
//输出: [[1]]
// 
//
// 
//
// 提示: 
//
// 
// 1 <= numRows <= 30 
// 
//
// Related Topics 数组 动态规划 👍 1335 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

//Java：杨辉三角
public class P118PascalsTriangle {
    public static void main(String[] args) {
        Solution solution = new P118PascalsTriangle().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> generate(int numRows) {
            int[][] dp = new int[numRows][numRows];
            dp[0][0] = 1;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numRows; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    int r = i - 1;
                    int c1 = j - 1;
                    int c2 = j;
                    dp[i][j] = (valid(r, c1, numRows) ? dp[r][c1] : 0) + (valid(r, c2, numRows) ? dp[r][c2] : 0);
                }
            }
            List<List<Integer>> res = new ArrayList<>();
            for (int i = 0; i < numRows; i++) {
                List<Integer> tmp = new ArrayList<>();
                for (int j = 0; j < numRows; j++) {
                    if (dp[i][j] != 0) {
                        tmp.add(dp[i][j]);
                    } else {
                        break;
                    }
                }
                res.add(tmp);
            }
            return res;
        }

        private boolean valid(int i, int j, int numRows) {
            return i >= 0 && i < numRows && j >= 0 && j < numRows;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
