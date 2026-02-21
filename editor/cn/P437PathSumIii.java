//给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。 
//
// 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
//输出：3
//解释：和等于 8 的路径有 3 条，如图所示。
// 
//
// 示例 2： 
//
// 
//输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//输出：3
// 
//
// 
//
// 提示: 
//
// 
// 二叉树的节点个数的范围是 [0,1000] 
// 
// -10⁹ <= Node.val <= 10⁹ 
// -1000 <= targetSum <= 1000 
// 
//
// Related Topics 树 深度优先搜索 二叉树 👍 2224 👎 0


package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

//Java：路径总和 III
public class P437PathSumIii {
    public static void main(String[] args) {
        Solution solution = new P437PathSumIii().new Solution();
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
        Map<Long, Integer> record = new HashMap<>();

        public int pathSum(TreeNode root, int targetSum) {
            record.put(0L, 1);
            return trave(root, targetSum, 0);
        }

        private int trave(TreeNode root, int targetSum, long curr) {
            if (root == null) {
                return 0;
            }
            curr += root.val;
            int res;
            res = record.getOrDefault(curr - targetSum, 0);
            record.put(curr, record.getOrDefault(curr, 0) + 1);
            res += trave(root.left, targetSum, curr);
            res += trave(root.right, targetSum, curr);
            record.put(curr, record.getOrDefault(curr, 0) - 1);
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
