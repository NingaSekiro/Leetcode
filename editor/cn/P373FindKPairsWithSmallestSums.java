//给定两个以 非递减顺序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。 
//
// 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。 
//
// 请找到和最小的 k 个数对 (u1,v1), (u2,v2) ... (uk,vk) 。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
//输出: [1,2],[1,4],[1,6]
//解释: 返回序列中的前 3 对数：
//     [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
// 
//
// 示例 2: 
//
// 
//输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
//输出: [1,1],[1,1]
//解释: 返回序列中的前 2 对数：
//     [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
// 
//
// 
//
// 提示: 
//
// 
// 1 <= nums1.length, nums2.length <= 10⁵ 
// -10⁹ <= nums1[i], nums2[i] <= 10⁹ 
// nums1 和 nums2 均为 升序排列 
// 
// 1 <= k <= 10⁴ 
// k <= nums1.length * nums2.length 
// 
//
// Related Topics 数组 堆（优先队列） 👍 625 👎 0


package leetcode.editor.cn;

import java.util.*;

//Java：查找和最小的 K 对数字
public class P373FindKPairsWithSmallestSums {
    public static void main(String[] args) {
        Solution solution = new P373FindKPairsWithSmallestSums().new Solution();
        int[] nums1 = new int[]{1, 7, 11};
        int[] nums2 = new int[]{2, 4, 6};
        solution.kSmallestPairs(nums1, nums2, 3);
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> res = new ArrayList<>();
            int l = 0, r = 0;
            res.add(Arrays.asList(nums1[l], nums2[r]));
            k--;
            while (k-- > 0) {
                if (l == nums1.length - 1){
                    r++;
                    res.add(Arrays.asList(nums1[l], nums2[++r]));
                    continue;
                }
                if (r == nums2.length - 1){
                    l++;
                    res.add(Arrays.asList(nums1[++l], nums2[r]));
                    continue;
                }
                if (nums1[l + 1] + nums2[r] < nums1[l] + nums2[r + 1]) {
                    res.add(Arrays.asList(nums1[++l], nums2[r]));
                }else {
                    res.add(Arrays.asList(nums1[l], nums2[++r]));
                }
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
