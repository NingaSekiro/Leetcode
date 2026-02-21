//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 6 
// -10 <= nums[i] <= 10 
// nums 中的所有整数 互不相同 
// 
//
// Related Topics 数组 回溯 👍 2988 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

//Java：全排列
public class P46Permutations {
    public static void main(String[] args) {
        Solution solution = new P46Permutations().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> tmp = new ArrayList<>();
            dfs(res, tmp, nums, 0);
            return res;
        }

        private void dfs(List<List<Integer>> res, List<Integer> tmp, int[] nums, int i) {
            if (i == nums.length) {
                res.add(new ArrayList<>(tmp));
                return;
            }
            for (int j = 0; j < nums.length; j++) {
                if (tmp.contains(nums[j])) {
                    continue;
                }
                tmp.add(nums[j]);
                dfs(res, tmp, nums, i + 1);
                tmp.remove(tmp.size() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
