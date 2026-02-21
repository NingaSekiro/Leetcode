//给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。 
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。 
//
// 
//
// 示例 1： 
// 
// 
//输入：board = [['A','B','C','E'],['S','F','C','S'],['A','D','E','E']], word = 
//"ABCCED"
//输出：true
// 
//
// 示例 2： 
// 
// 
//输入：board = [['A','B','C','E'],['S','F','C','S'],['A','D','E','E']], word = 
//"SEE"
//输出：true
// 
//
// 示例 3： 
// 
// 
//输入：board = [['A','B','C','E'],['S','F','C','S'],['A','D','E','E']], word = 
//"ABCB"
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == board.length 
// n = board[i].length 
// 1 <= m, n <= 6 
// 1 <= word.length <= 15 
// board 和 word 仅由大小写英文字母组成 
// 
//
// 
//
// 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？ 
//
// Related Topics 深度优先搜索 数组 字符串 回溯 矩阵 👍 2133 👎 0


package leetcode.editor.cn;

//Java：单词搜索
public class P79WordSearch {
    public static void main(String[] args) {
        Solution solution = new P79WordSearch().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int row;
        int col;
        int[] rdi = new int[]{0, 1, 0, -1};
        int[] cdi = new int[]{1, 0, -1, 0};
        boolean[][] visited;

        public boolean exist(char[][] board, String word) {
            row = board.length;
            col = board[0].length;
            visited = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == word.charAt(0)) {
                        visited[i][j] = true;
                        if (backtrack(board, word, i, j, 1)) {
                            return true;
                        }
                        visited[i][j] = false;
                    }
                }
            }
            return false;
        }

        private boolean backtrack(char[][] board, String word, int r, int c, int index) {
            if (index == word.length()) {
                return true;
            }
            boolean flag = false;
            for (int i = 0; i < 4; i++) {
                int rtmp = r + rdi[i];
                int ctmp = c + cdi[i];
                if (valid(rtmp, ctmp) && !visited[rtmp][ctmp] && board[rtmp][ctmp] == word.charAt(index)) {
                    visited[rtmp][ctmp] = true;
                    if (backtrack(board, word, rtmp, ctmp, index + 1)) {
                        flag = true;
                    }
                    visited[rtmp][ctmp] = false;
                }
            }
            return flag;

        }

        private boolean valid(int r, int c) {
            return r >= 0 && r < row && c >= 0 && c < col;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
