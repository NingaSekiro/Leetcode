//给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。 
//
// 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。 
//
// 此外，你可以假设该网格的四条边均被水包围。 
//
// 
//
// 示例 1： 
//
// 
//输入：grid = [
//  ['1','1','1','1','0'],
//  ['1','1','0','1','0'],
//  ['1','1','0','0','0'],
//  ['0','0','0','0','0']
//]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：grid = [
//  ['1','1','0','0','0'],
//  ['1','1','0','0','0'],
//  ['0','0','1','0','0'],
//  ['0','0','0','1','1']
//]
//输出：3
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 300 
// grid[i][j] 的值为 '0' 或 '1' 
// 
//
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 2925 👎 0


package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.Queue;

//Java：岛屿数量
public class P200NumberOfIslands {
    public static void main(String[] args) {
        Solution solution = new P200NumberOfIslands().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int row;
        int col;
        int[][] dire = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public int numIslands(char[][] grid) {
            int res = 0;
            row = grid.length;
            col = grid[0].length;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        bfs(grid, i, j);
                        res++;
                    }
                }
            }
            return res;
        }

        private void dfs(char[][] grid, int i, int j) {
            if (i < 0 || j < 0 || i >= row || j >= col || grid[i][j] == '0') {
                return;
            }
            grid[i][j] = '0';
            for (int[] ints : dire) {
                dfs(grid, i + ints[0], j + ints[1]);
            }
        }

        private void bfs(char[][] grid, int i, int j) {
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{i, j});
            while (!queue.isEmpty()) {
                int[] poll = queue.poll();
                i = poll[0]; j = poll[1];
                if (0 <= i && i < grid.length && 0 <= j && j < grid[0].length && grid[i][j] == '1') {
                    grid[i][j] = '0';
                    for (int[] ints : dire) {
                        queue.offer(new int[]{i + ints[0], j + ints[1]});
                    }
                }
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
