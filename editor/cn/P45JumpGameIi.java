//给定一个长度为 n 的 0 索引整数数组 nums。初始位置在下标 0。 
//
// 每个元素 nums[i] 表示从索引 i 向后跳转的最大长度。换句话说，如果你在索引 i 处，你可以跳转到任意 (i + j) 处： 
//
// 
// 0 <= j <= nums[i] 且 
// i + j < n 
// 
//
// 返回到达 n - 1 的最小跳跃次数。测试用例保证可以到达 n - 1。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [2,3,1,1,4]
//输出: 2
//解释: 跳到最后一个位置的最小跳跃数是 2。
//     从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
// 
//
// 示例 2: 
//
// 
//输入: nums = [2,3,0,1,4]
//输出: 2
// 
//
// 
//
// 提示: 
//
// 
// 1 <= nums.length <= 10⁴ 
// 0 <= nums[i] <= 1000 
// 题目保证可以到达 n - 1 
// 
//
// Related Topics 贪心 数组 动态规划 👍 2971 👎 0


package leetcode.editor.cn;

//Java：跳跃游戏 II
public class P45JumpGameIi {
    public static void main(String[] args) {
        Solution solution = new P45JumpGameIi().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int jump(int[] nums) {
            int[] dp = new int[nums.length];
            dp[0] = 0;
            for (int i = 0; i < nums.length; i++) {
                for (int j = 1; j <= nums[i]; j++) {
                    if (i + j < nums.length) {
                        if (dp[i + j] != 0) {
                            dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
                        } else {
                            dp[i + j] = dp[i] + 1;
                        }
                    }
                }
            }
            return dp[nums.length - 1];
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
