//给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。 
//
// 如果数组中不存在目标值 target，返回 [-1, -1]。 
//
// 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//
// 
//输入：nums = [], target = 0
//输出：[-1,-1] 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// nums 是一个非递减数组 
// -10⁹ <= target <= 10⁹ 
// 
//
// Related Topics 数组 二分查找 👍 3199 👎 0


package leetcode.editor.cn;

//Java：在排序数组中查找元素的第一个和最后一个位置
public class P34FindFirstAndLastPositionOfElementInSortedArray {
    public static void main(String[] args) {
        Solution solution = new P34FindFirstAndLastPositionOfElementInSortedArray().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int[] searchRange(int[] nums, int target) {
            int start = notLessFirstIndex(nums, target);
            if (start >= nums.length || nums[start] != target) {
                return new int[]{-1, -1};
            }
            int end = notLessFirstIndex(nums, target + 1);
            return new int[]{start, end - 1};
        }

        //        left（不包含left）的左边都小于 x (从左往右第一个大于等于的）
//        right（不包含right）的右边都大于等于 x。（从右往左第一个小于的）
//        private int notLessFirstIndex(int[] nums, int target) {
//            int l = 0, r = nums.length - 1;
//            while (l <= r) {
//                int mid = l + (r - l) / 2;
//                if (nums[mid] < target) {
//                    l = mid + 1;
//                } else {
//                    r = mid - 1;
//                }
//            }
//            return l;
//        }

        private int notLessFirstIndex(int[] nums, int target) {
            int l = -1, r = nums.length;
            while (l + 1 != r) {
                int mid = l + (r - l) / 2;
                if (nums[mid] < target) {
                    l = mid;
                } else {
                    r = mid;
                }
            }
            return r;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
