//给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续 子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。 
//
// 测试用例的答案是一个 32-位 整数。 
//
// 请注意，一个只包含一个元素的数组的乘积是这个元素的值。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [2,3,-2,4]
//输出: 6
//解释: 子数组 [2,3] 有最大乘积 6。
// 
//
// 示例 2: 
//
// 
//输入: nums = [-2,0,-1]
//输出: 0
//解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。 
//
// 
//
// 提示: 
//
// 
// 1 <= nums.length <= 2 * 10⁴ 
// -10 <= nums[i] <= 10 
// nums 的任何子数组的乘积都 保证 是一个 32-位 整数 
// 
//
// Related Topics 数组 动态规划 👍 2535 👎 0


package leetcode.editor.cn;

//Java：乘积最大子数组
public class P152MaximumProductSubarray {
    public static void main(String[] args) {
        Solution solution = new P152MaximumProductSubarray().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int maxProduct(int[] nums) {
            int length = nums.length;
            long[] maxF = new long[length];
            long[] minF = new long[length];
            for (int i = 0; i < length; i++) {
                maxF[i] = nums[i];
                minF[i] = nums[i];
            }
            for (int i = 1; i < length; ++i) {
                maxF[i] = Math.max(maxF[i - 1] * nums[i], Math.max(nums[i], minF[i - 1] * nums[i]));
                minF[i] = Math.min(minF[i - 1] * nums[i], Math.min(nums[i], maxF[i - 1] * nums[i]));
            }
            int ans = (int) maxF[0];
            for (int i = 1; i < length; ++i) {
                ans = Math.max(ans, (int) maxF[i]);
            }
            return ans;
        }
    }

//leetcode submit region end(Prohibit modification and deletion)

}
