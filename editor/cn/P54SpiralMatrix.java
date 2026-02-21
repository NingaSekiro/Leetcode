//给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。 
//
// 
//
// 示例 1： 
// 
// 
//输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[1,2,3,6,9,8,7,4,5]
// 
//
// 示例 2： 
// 
// 
//输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 10 
// -100 <= matrix[i][j] <= 100 
// 
//
// Related Topics 数组 矩阵 模拟 👍 2068 👎 0


package leetcode.editor.cn;


import java.util.ArrayList;
import java.util.List;

//Java：螺旋矩阵
public class P54SpiralMatrix {
    public static void main(String[] args) {
        Solution solution = new P54SpiralMatrix().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            int row = matrix.length;
            int col = matrix[0].length;
            List<Integer> res = new ArrayList<>();
            int i = 0, j = 0, di = 0;
            int[][] direction = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int k = 0; k < row * col; k++) {
                res.add(matrix[i][j]);
                matrix[i][j] = Integer.MAX_VALUE;
                int nexti = i + direction[di % 4][0];
                int nextj = j + direction[di % 4][1];
                if (nexti >= row || nextj >= col || nexti < 0 || nextj < 0 || matrix[nexti][nextj] == Integer.MAX_VALUE) {
                    di++;
                }
                i = i + direction[di % 4][0];
                j = j + direction[di % 4][1];
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
