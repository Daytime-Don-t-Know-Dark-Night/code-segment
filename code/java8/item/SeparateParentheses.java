package java8.item;

import java.util.Arrays;

/**
 * @author chao
 * @date 2023/2/12 19:50
 * @desc
 */
public class SeparateParentheses {

    public static void main(String[] args) {
        String[] res = separateParentheses("a=b)");
        Arrays.stream(res).forEach(System.out::println);
    }

    public static String[] separateParentheses(String str) {
        String[] result = new String[2];
        int equalsIndex = str.indexOf('=');

        int leftParenthesesCount = 0;
        for (int i = equalsIndex - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '(') {
                leftParenthesesCount++;
            } else if (c == ')') {
                leftParenthesesCount--;
            }
        }

        int rightParenthesesCount = 0;
        for (int i = equalsIndex + 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                rightParenthesesCount++;
            } else if (c == ')') {
                rightParenthesesCount--;
            }
        }

        if (leftParenthesesCount > 0) {
            int lastIndex = str.lastIndexOf('(');
            result[0] = str.substring(lastIndex);
            result[1] = str.substring(0, lastIndex);
        } else if (rightParenthesesCount > 0) {
            int firstIndex = str.indexOf(')');
            result[0] = str.substring(0, firstIndex);
            result[1] = str.substring(firstIndex);
        } else {
            result[0] = str;
        }

        return result;
    }





}
