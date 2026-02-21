//在给定的 m x n 网格
// grid 中，每个单元格可以有以下三个值之一： 
//
// 
// 值 0 代表空单元格； 
// 值 1 代表新鲜橘子； 
// 值 2 代表腐烂的橘子。 
// 
//
// 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。 
//
// 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：grid = [[2,1,1],[1,1,0],[0,1,1]]
//输出：4
// 
//
// 示例 2： 
//
// 
//输入：grid = [[2,1,1],[0,1,1],[1,0,1]]
//输出：-1
//解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个方向上。
// 
//
// 示例 3： 
//
// 
//输入：grid = [[0,2]]
//输出：0
//解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 10 
// grid[i][j] 仅为 0、1 或 2 
// 
//
// Related Topics 广度优先搜索 数组 矩阵 👍 1120 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Java：腐烂的橘子
public class P994RottingOranges {
    public static void main(String[] args) {

        Solution solution = new P994RottingOranges().new Solution();
        // 构造测试用例 grid = [[2,1,1],[1,1,0],[0,1,1]]
        int[][] grid = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        };
        int result = solution.orangesRotting(grid);
        System.out.println("Result: " + result);
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int row;
        int col;
        int[][] dire = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int good;
        Queue<int[]> bad = new LinkedList<>();

        public int orangesRotting(int[][] grid) {
            int res = 0;
            row = grid.length;
            col = grid[0].length;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == 1) {
                        good++;
                    } else if (grid[i][j] == 2) {
                        bad.add(new int[]{i, j});
                    }
                }
            }
            while (good > 0 && bad.size() > 0) {
                res++;
                int size = bad.size();
                for (int i = 0; i < size; i++) {
                    int[] poll = bad.poll();
                    for (int[] ints : dire) {
                        int r = poll[0] + ints[0];
                        int c = poll[1] + ints[1];
                        if (valid(r, c) && grid[r][c] == 1) {
                            good--;
                            grid[r][c] = 2;
                            bad.add(new int[]{r, c});
                        }
                    }
                }
            }
            return good > 0 ? -1 : res;
        }

        private boolean valid(int r, int c) {
            return r >= 0 && r < row && c >= 0 && c < col;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
