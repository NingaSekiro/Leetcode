//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。 
//
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。 
//
// 
//
// 
//
// 示例 1： 
//
// 
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
// 
//
// 示例 2： 
//
// 
//输入：digits = ""
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：digits = "2"
//输出：["a","b","c"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= digits.length <= 4 
// digits[i] 是范围 ['2', '9'] 的一个数字。 
// 
//
// Related Topics 哈希表 字符串 回溯 👍 2943 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Java：电话号码的字母组合
public class P17LetterCombinationsOfAPhoneNumber {
    public static void main(String[] args) {
        Solution solution = new P17LetterCombinationsOfAPhoneNumber().new Solution();
        // TO TEST
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        Map<Character, String> map = new HashMap<>();

        {
            map.put('2', "abc");
            map.put('3', "def");
            map.put('4', "ghi");
            map.put('5', "jkl");
            map.put('6', "mno");
            map.put('7', "pqrs");
            map.put('8', "tuv");
            map.put('9', "wxyz");
            map.put('1', "");
        }

        public List<String> letterCombinations(String digits) {
            List<String> res = new ArrayList<>();
            if (digits.length() == 0) {
                return res;
            }
            StringBuilder stringBuilder = new StringBuilder();
            dfs(res, digits, stringBuilder, 0);
            return res;
        }

        private void dfs(List<String> res, String digits, StringBuilder stringBuilder, int index) {
            if (index == digits.length()) {
                res.add(stringBuilder.toString());
                return;
            }
            String letters = map.get(digits.charAt(index));
            for (int i = 0; i < letters.length(); i++) {
                stringBuilder.append(letters.charAt(i));
                dfs(res, digits, stringBuilder, index + 1);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
