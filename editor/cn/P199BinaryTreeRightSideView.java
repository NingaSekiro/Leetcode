//给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。 
//
// 
//
// 示例 1： 
//
// 
// 输入：root = [1,2,3,null,5,null,4] 
// 
//
// 输出：[1,3,4] 
//
// 解释： 
//
// 
//
// 示例 2： 
//
// 
// 输入：root = [1,2,3,4,null,null,null,5] 
// 
//
// 输出：[1,3,4,5] 
//
// 解释： 
//
// 
//
// 示例 3： 
//
// 
// 输入：root = [1,null,3] 
// 
//
// 输出：[1,3] 
//
// 示例 4： 
//
// 
// 输入：root = [] 
// 
//
// 输出：[] 
//
// 
//
// 提示: 
//
// 
// 二叉树的节点个数的范围是 [0,100] 
// 
// -100 <= Node.val <= 100 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1290 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Java：二叉树的右视图
public class P199BinaryTreeRightSideView {
    public static void main(String[] args) {
        Solution solution = new P199BinaryTreeRightSideView().new Solution();
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    class Solution {
        public List<Integer> rightSideView(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            List<Integer> res = new ArrayList<>();
            if (root == null) {
                return res;
            }
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode poll = queue.poll();
                    if (i == size - 1) {
                        res.add(poll.val);
                    }
                    if (poll.left != null) {
                        queue.offer(poll.left);
                    }
                    if (poll.right != null) {
                        queue.offer(poll.right);
                    }
                }
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
