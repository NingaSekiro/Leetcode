//给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "cbaebabacd", p = "abc"
//输出: [0,6]
//解释:
//起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
//起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
// 
//
// 示例 2: 
//
// 
//输入: s = "abab", p = "ab"
//输出: [0,1,2]
//解释:
//起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
//起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
//起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
// 
//
// 
//
// 提示: 
//
// 
// 1 <= s.length, p.length <= 3 * 10⁴ 
// s 和 p 仅包含小写字母 
// 
//
// Related Topics 哈希表 字符串 滑动窗口 👍 1868 👎 0


package leetcode.editor.cn;

import javax.swing.*;
import java.util.*;

//Java：找到字符串中所有字母异位词
public class P438FindAllAnagramsInAString {
    public static void main(String[] args) {
        Solution solution = new P438FindAllAnagramsInAString().new Solution();
        System.out.println(solution.findAnagrams("baa", "aa"));
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<Integer> findAnagrams(String s, String p) {
            if (s.length() < p.length()) {
                return new ArrayList<>();
            }
            Map<Character, Integer> target = new HashMap<>();
            Map<Character, Integer> source = new HashMap<>();
            int l = 0;
            List<Integer> res = new ArrayList<>();
            for (char c : p.toCharArray()) {
                target.put(c, target.getOrDefault(c, 0) + 1);
            }
            for (int i = 0; i < p.length()-1; i++) {
                char c = s.charAt(i);
                source.put(c, source.getOrDefault(c, 0) + 1);
            }
            while (l <= s.length() - p.length()) {
                char right = s.charAt(l + p.length() - 1);
                source.put(right, source.getOrDefault(right, 0) + 1);
                if (check(source,target)) {
                    res.add(l);
                }
                char c = s.charAt(l);
                source.put(c, source.getOrDefault(c, 0) - 1);
                if (source.get(c) == 0) {
                    source.remove(c);
                }
                l++;

            }
            return res;
        }

        private boolean check(Map<Character, Integer> source, Map<Character, Integer> target) {
            for (Map.Entry<Character, Integer> entry : target.entrySet()) {
                if ( !source.containsKey(entry.getKey()) || !Objects.equals(source.get(entry.getKey()), entry.getValue())) {
                    return false;
                }
            }
            return true;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
