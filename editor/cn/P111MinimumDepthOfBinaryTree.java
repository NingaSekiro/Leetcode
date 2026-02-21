//给定一个二叉树，找出其最小深度。 
//
// 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。 
//
// 说明：叶子节点是指没有子节点的节点。 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：root = [2,null,3,null,4,null,5,null,6]
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数的范围在 [0, 10⁵] 内 
// -1000 <= Node.val <= 1000 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1183 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;

//Java：二叉树的最小深度
public class P111MinimumDepthOfBinaryTree {
    public static void main(String[] args) {
        Solution solution = new P111MinimumDepthOfBinaryTree().new Solution();
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

        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            LinkedList<TreeNode> linkedList = new LinkedList<>();
            linkedList.add(root);
            int depth = 0;
            while (!linkedList.isEmpty()) {
                depth++;
                int size = linkedList.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = linkedList.pollFirst();
                    // 如果是叶子节点，直接返回当前深度
                    if (node.left == null && node.right == null) {
                        return depth;
                    }

                    // 将子节点加入队列
                    if (node.left != null) {
                        linkedList.offer(node.left);
                    }
                    if (node.right != null) {
                        linkedList.offer(node.right);
                    }
                }

            }
            return 0;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}