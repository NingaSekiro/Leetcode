//给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。 
//
// 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。 
//
// 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。 
//
// 
//
// 示例 1: 
//
// 
//输入: [3,2,1,5,6,4], k = 2
//输出: 5
// 
//
// 示例 2: 
//
// 
//输入: [3,2,3,1,2,4,5,5,6], k = 4
//输出: 4 
//
// 
//
// 提示： 
//
// 
// 1 <= k <= nums.length <= 10⁵ 
// -10⁴ <= nums[i] <= 10⁴ 
// 
//
// Related Topics 数组 分治 快速选择 排序 堆（优先队列） 👍 2447 👎 0

package leetcode.editor.cn;

import java.util.PriorityQueue;

//Java：数组中的第K个最大元素
public class P215KthLargestElementInAnArray {
    public static void main(String[] args) {
        Solution solution = new P215KthLargestElementInAnArray().new Solution();
        solution.findKthLargest(new int[]{7, 6, 5, 4, 3, 2, 1}, 2);
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int length;

        public int findKthLargest(int[] nums, int k) {
            length = nums.length;
//            return quickselect(nums, 0, nums.length - 1, nums.length - k);
            buildMaxHeap(nums);
            for (int i = nums.length - 1; i > nums.length - k; i--) {
                swap(nums, 0, i);
                length--;
                maxHeap(nums, 0);
            }
            return nums[0];
        }

        void buildMaxHeap(int[] nums) {
            for (int i = length / 2 - 1; i >= 0; i--) {
                maxHeap(nums, i);
            }
        }

        void maxHeap(int[] nums, int index) {
            int l = index * 2 + 1;
            int r = index * 2 + 2;
            int largest = index;
            if (l < length && nums[l] > nums[largest]) {
                largest = l;
            }
            if (r < length && nums[r] > nums[largest]) {
                largest = r;
            }
            if (largest != index) {
                swap(nums, index, largest);
                maxHeap(nums, largest);
            }
        }

        public void swap(int[] a, int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }


        int quickselect(int[] nums, int l, int r, int k) {
            if (l == r) return nums[k];
            int x = nums[l], i = l - 1, j = r + 1;
            while (i < j) {
                do i++; while (nums[i] < x);
                do j--; while (nums[j] > x);
                if (i < j) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
            if (k <= j) return quickselect(nums, l, j, k);
            else return quickselect(nums, j + 1, r, k);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}