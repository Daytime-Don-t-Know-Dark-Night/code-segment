package item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chao
 * @date 2023/2/12 14:50
 * @desc
 */
public class StringSplitter {

    // https://chat.openai.com/chat

    public static void main(String[] args) {
        String str = "abcde";
        List<List<String>> result = split(str);
        System.out.println(result);
    }

    public static List<List<String>> split(String str) {

        List<List<String>> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            result.add(new ArrayList<>());
            return result;
        }

        for (int i = 1; i <= str.length(); i++) {
            String left = str.substring(0, i);
            String right = str.substring(i);
            List<List<String>> rightResult = split(right);
            for (List<String> r : rightResult) {
                List<String> current = new ArrayList<>();
                current.add(left);
                current.addAll(r);
                result.add(current);
            }
        }
        return result;
    }

}
