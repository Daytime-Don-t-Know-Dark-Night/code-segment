package com.boluo;

import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 * <p>
 * 进阶：
 * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 * <p>
 * <p>
 * 示例 1：
 * 输入：s = "abc", t = "ahbgdc"
 * 输出：true
 * <p>
 * 示例 2：
 * 输入：s = "axc", t = "ahbgdc"
 * 输出：false
 */

public class LeetCode392 {

	public static void main(String[] args) {
		String s = "abc";
		String t = "ahbgdc";
		Assert.assertTrue(isSubsequence(s, t));
	}

	// 单次遍历
	public static boolean isSubsequence(String s, String t) {
		// 创建s的数组array, 遍历t, 如果array[0] = t[i], 就把array[0]删除, 最后如果数组为空, 则为true
		List<Character> sc = new String(s.toCharArray()).chars().mapToObj(i -> (char) i).collect(Collectors.toList());
		for (char c : t.toCharArray()) {
			if (!sc.isEmpty() && sc.get(0) == c) {
				sc.remove(0);
			}
		}
		return sc.isEmpty();
	}

	// 双指针解法
	public static boolean isSubsequence2(String s, String t) {

		throw new UnsupportedOperationException();
	}
}
