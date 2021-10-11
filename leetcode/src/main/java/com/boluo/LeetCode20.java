package com.boluo;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * <p>
 * 示例 1：
 * 输入：s = "()"
 * 输出：true
 * <p>
 * 示例 2：
 * 输入：s = "()[]{}"
 * 输出：true
 * <p>
 * 示例 3：
 * 输入：s = "(]"
 * 输出：false
 * <p>
 * 示例 4：
 * 输入：s = "([)]"
 * 输出：false
 * <p>
 * 示例 5：
 * 输入：s = "{[]}"
 * 输出：true
 */

public class LeetCode20 {

	public static void main(String[] args) {
		System.out.println(isValid("{()[()]}"));
	}

	public static boolean isValid(String s) {

		// 首先, 如果使用左括号和右括号的数量判断是否相同是不对的, 因为与括号的位置有关
		// 对于有效的括号, 他的部分子表达式也是有效的括号, 比如{()[()]}是一个有效的括号, ()[()]也是有效的括号, [()]...
		// 并且当我们每次删除一个最小的括号对时, 我们会逐渐将括号删除完, 例如

		// {()[()]}
		// {  [()]}
		// {  [  ]}
		// {      }
		//

		// 因此我们使用栈, 当遇到匹配的最小括号对时, 我们将这对括号从栈中删除(出栈), 如果最后栈为空, 则为有效的括号, 反之则不是, 具体顺序如下
		// { 入栈
		// ( 入栈
		// ) 出栈
		// [ 入栈
		// ( 入栈
		// ) 出栈
		// ] 出栈
		// } 出栈

		Map<Character, Integer> map = new HashMap<Character, Integer>() {{
			put('(', 1);
			put('[', 2);
			put('{', 3);
			put(')', 4);
			put(']', 5);
			put('}', 6);
		}};

		Stack<Character> stack = new Stack<>();
		boolean is_true = true;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int flag = map.get(c);
			if (flag >= 1 && flag <= 3) {
				stack.push(c);
			} else if (!stack.isEmpty() && map.get(stack.get(stack.size() - 1)) == flag - 3) {
				stack.pop();
			} else {
				is_true = false;
				break;
			}
		}

		if (!stack.isEmpty()) {
			is_true = false;
		}
		return is_true;
	}
}
