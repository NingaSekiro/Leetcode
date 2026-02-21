package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public static void main(String[] args) {
        Solution solution = new Permutations().new Solution();
        List<List<Integer>> permute = solution.permute(new int[]{1});
        System.out.println(permute);
    }

    public class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            int[] visit = new int[nums.length];
            dfs(res, new ArrayList<>(), nums, visit);
            return res;
        }


        void dfs(List<List<Integer>> res, List<Integer> tmp, int[] nums, int[] visit) {
            if (tmp.size() == nums.length) {
                res.add(new ArrayList<>(tmp));
            }
            for (int i = 0; i < nums.length; i++) {
                if (visit[i] == 0) {
                    tmp.add(nums[i]);
                    visit[i] = 1;
                    dfs(res, tmp, nums, visit);
                    tmp.remove(tmp.size() - 1);
                    visit[i] = 0;
                }
            }
        }
    }
}