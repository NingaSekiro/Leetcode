//给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
// 输入：nums = [1,1,1,2,2,3], k = 2 
// 
//
// 输出：[1,2] 
//
// 示例 2： 
//
// 
// 输入：nums = [1], k = 1 
// 
//
// 输出：[1] 
//
// 示例 3： 
//
// 
// 输入：nums = [1,2,1,2,1,2,3,1,3,2], k = 2 
// 
//
// 输出：[1,2] 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// -10⁴ <= nums[i] <= 10⁴ 
// k 的取值范围是 [1, 数组中不相同的元素的个数] 
// 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的 
// 
//
// 
//
// 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。 
//
// Related Topics 数组 哈希表 分治 桶排序 计数 快速选择 排序 堆（优先队列） 👍 2131 👎 0


package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

//Java：前 K 个高频元素
public class P347TopKFrequentElements {
    public static void main(String[] args) {
        Solution solution = new P347TopKFrequentElements().new Solution();
        solution.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        Map<Integer, Integer> map = new HashMap<>();

        public int[] topKFrequent(int[] nums, int k) {
            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
            int[] heap = new int[map.size()];
            int id = 0;
            for (Integer i : map.keySet()) {
                heap[id++] = i;
            }

            buildMaxHeap(heap, heap.length);
            int length = heap.length;
            for (int i = heap.length - 1; i >= heap.length - k ; i--) {
                length--;
                swap(heap, 0, i);
                maxHeap(heap, 0, length);
            }
            int[] res = new int[k];
            for (int i = 0; i < k; i++) {
                res[i] = heap[heap.length - 1 - i];
            }
            return res;
        }


        private void buildMaxHeap(int[] heap, int length) {
            for (int i = length / 2 - 1; i >= 0; i--) {
                maxHeap(heap, i, length);
            }
        }

        private void maxHeap(int[] heap, int index, int length) {
            int l = index * 2 + 1;
            int r = index * 2 + 2;
            int largest = index;
            if (l < length && map.get(heap[l]) > map.get(heap[largest])) {
                largest = l;
            }
            if (r < length && map.get(heap[r]) > map.get(heap[largest])) {
                largest = r;
            }
            if (largest != index) {
                swap(heap, largest, index);
                maxHeap(heap, largest, length);
            }
        }

        private void swap(int[] heap, int l, int r) {
            int tmp = heap[l];
            heap[l] = heap[r];
            heap[r] = tmp;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}
