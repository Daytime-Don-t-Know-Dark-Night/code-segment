package com.boluo;

import org.junit.Assert;

/**
 * 给定两个字符串 s 和 t，它们只包含小写字母。
 * 字符串t由字符串s随机重排，然后在随机位置添加一个字母。
 * 请找出在 t 中被添加的字母。
 * <p>
 * 示例 1：
 * 输入：s = "abcd", t = "abcde"
 * 输出："e"
 * 解释：'e' 是那个被添加的字母。
 * <p>
 * 示例 2：
 * 输入：s = "", t = "y"
 * 输出："y"
 * <p>
 * 示例 3：
 * 输入：s = "a", t = "aa"
 * 输出："a"
 * <p>
 * 示例 4：
 * 输入：s = "ae", t = "aea"
 * 输出："a"
 */

public class LeetCode389 {

	public static void main(String[] args) {
		char c = findTheDifference("abcd", "abcde");
		Assert.assertEquals(c, 'e');
	}

	// https://leetcode-cn.com/problems/find-the-difference/solution/yi-ju-hua-zhao-bu-tong-reduce-gao-qi-lai-eqok/
	public static char findTheDifference(String s, String t) {
		int single = 0;
		for (char c : (s + t).toCharArray()) {
			single ^= c;
		}
		return (char) single;
	}
}
