package com.boluo;

/**
 * 实现strStr()函数。
 * 给你两个字符串haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回 -1 。
 * <p>
 * 说明：
 * 当needle是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 * 对于本题而言，当 needle是空字符串时我们应当返回 0。这与 C 语言的strstr()以及 Java 的indexOf()定义相符。
 * <p>
 * 示例 1：
 * 输入：haystack = "hello", needle = "ll"
 * 输出：2
 * <p>
 * 示例 2：
 * 输入：haystack = "aaaaa", needle = "bba"
 * 输出：-1
 * <p>
 * 示例 3：
 * 输入：haystack = "", needle = ""
 * 输出：0
 */

public class LeetCode28 {

	public static void main(String[] args) {
		System.out.println(strStr("hello", "ll"));
		System.out.println(strStr("aaaaa", "bba"));
		System.out.println(strStr("", ""));
		System.out.println(strStr("a", "a"));
	}

	public static int strStr(String haystack, String needle) {

		if (needle == null || needle.length() == 0) {
			return 0;
		}

		for (int i = 0; i <= haystack.length() - needle.length(); i++) {
			boolean br = true;
			for (int j = 0; j < needle.length(); j++) {
				if (haystack.charAt(i + j) != needle.charAt(j)) {
					br = false;
					break;
				}
			}
			if (br) {
				return i;
			}
		}
		return -1;
	}

	// TODO KMP算法
	public static int strStr2(String haystack, String needle) {
		throw new UnsupportedOperationException();
	}
}
