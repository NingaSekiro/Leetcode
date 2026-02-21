//给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。 
//
// 子数组是数组中元素的连续非空序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,1], k = 2
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3], k = 3
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2 * 10⁴ 
// -1000 <= nums[i] <= 1000 
// -10⁷ <= k <= 10⁷ 
// 
//
// Related Topics 数组 哈希表 前缀和 👍 3025 👎 0


package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

//Java：和为 K 的子数组
public class P560SubarraySumEqualsK {
    public static void main(String[] args) {
        Solution solution = new P560SubarraySumEqualsK().new Solution();
        solution.subarraySum(new int[]{-2,1,-3,4,-1,2,1,-5,4}, 2);
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int subarraySum(int[] nums, int k) {

            // 注意前缀和数组首位为0，长度为n+1
            int[] preSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }
            int count = 0;
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : preSum) {
                if (map.containsKey(i - k)) {
                    count += map.get(i - k);
                }
                map.put(i, map.getOrDefault(i, 0) + 1);
            }
            return count;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
