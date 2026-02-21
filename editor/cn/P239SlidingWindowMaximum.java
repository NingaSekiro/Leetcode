//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
//。 
//
// 返回 滑动窗口中的最大值 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
// 
//
// 示例 2： 
//
// 
//输入：nums = [1], k = 1
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// -10⁴ <= nums[i] <= 10⁴ 
// 1 <= k <= nums.length 
// 
//
// Related Topics 队列 数组 滑动窗口 单调队列 堆（优先队列） 👍 3223 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Java：滑动窗口最大值
public class P239SlidingWindowMaximum {
    public static void main(String[] args) {
        Solution solution = new P239SlidingWindowMaximum().new Solution();
        // TO TEST
        int[] ints = {1, 3, -1, -3, 5, 3, 6, 7};
        solution.maxSlidingWindow(ints, 3);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int length = nums.length;
            LinkedList<Integer> deque = new LinkedList<>();
            int l = 0, r = 0;
            List<Integer> integers = new ArrayList<>();
            while (r < k) {
                // 把大于自己的都去除就是递减队列了
                while (!deque.isEmpty() && nums[deque.getLast()] <= nums[r]) {
                    deque.pollLast();
                }
                deque.addLast(r++);
            }
            integers.add(nums[deque.getFirst()]);
            for (; r < length; r++) {
                while (!deque.isEmpty() && nums[deque.getLast()] <= nums[r]) {
                    deque.pollLast();
                }
                deque.addLast(r);
                while (deque.getFirst() <= r - k) {
                    deque.pollFirst();
                }
                integers.add(nums[deque.getFirst()]);
            }
            return integers.stream().mapToInt(Integer::intValue).toArray();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
