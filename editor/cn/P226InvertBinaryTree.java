//给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：root = [4,2,7,1,3,6,9]
//输出：[4,7,2,9,6,3,1]
// 
//
// 示例 2： 
//
// 
//
// 
//输入：root = [2,1,3]
//输出：[2,3,1]
// 
//
// 示例 3： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目范围在 [0, 100] 内 
// -100 <= Node.val <= 100 
// 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1788 👎 0

package leetcode.editor.cn;

//Java：翻转二叉树
public class P226InvertBinaryTree {
    public static void main(String[] args) {
        Solution solution = new P226InvertBinaryTree().new Solution();
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
        public TreeNode invertTree(TreeNode root) {
            dfs(root);
            return root;
        }


        void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            TreeNode tmp = root.left;
            root.left = root.right;
            root.right = tmp;
            dfs(root.left);
            dfs(root.right);
        }

/*        这段代码是一个简单的交换函数，接受两个节点 l 和 r，并将它们互换。在函数内部，我们创建了一个临时变量 tmp，
        用于交换两个节点的值。但是注意，这里的 l 和 r 是函数的局部变量，它们只是形参，对于函数外部的实际参数并没有影响。*/
//        void swap(TreeNode l, TreeNode r) {
//            TreeNode tmp = l;
//            l = r;
//            r = tmp;
//        }

    }
//leetcode submit region end(Prohibit modification and deletion)


}