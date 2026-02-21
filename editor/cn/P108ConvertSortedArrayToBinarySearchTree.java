//给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。 
//
// 
//
// 示例 1： 
// 
// 
//输入：nums = [-10,-3,0,5,9]
//输出：[0,-3,9,-10,null,5]
//解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
//
// 
//
// 示例 2： 
// 
// 
//输入：nums = [1,3]
//输出：[3,1]
//解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁴ 
// -10⁴ <= nums[i] <= 10⁴ 
// nums 按 严格递增 顺序排列 
// 
//
// Related Topics 树 二叉搜索树 数组 分治 二叉树 👍 1769 👎 0


package leetcode.editor.cn;

//Java：将有序数组转换为二叉搜索树
public class P108ConvertSortedArrayToBinarySearchTree {
    public static void main(String[] args) {
        Solution solution = new P108ConvertSortedArrayToBinarySearchTree().new Solution();
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
        public TreeNode sortedArrayToBST(int[] nums) {
            return trave(0, nums.length - 1, nums);
        }

        TreeNode trave(int l, int r, int[] nums) {
            if (l > r) {
                return null;
            }
            int mid = l + (r - l) / 2;
            TreeNode treeNode = new TreeNode(nums[mid]);
            treeNode.left = trave(l, mid - 1, nums);
            treeNode.right = trave(mid + 1, r, nums);
            return treeNode;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
