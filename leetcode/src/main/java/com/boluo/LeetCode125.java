package com.boluo;

import com.google.common.base.Strings;
import org.junit.Assert;

/**
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 * <p>
 * 示例 1:
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 解释："amanaplanacanalpanama" 是回文串
 * <p>
 * 示例 2:
 * 输入: "race a car"
 * 输出: false
 * 解释："raceacar" 不是回文串
 */

public class LeetCode125 {

	public static void main(String[] args) {

		Assert.assertTrue(isPalindrome("A man, a plan, a canal: Panama"));
		Assert.assertFalse(isPalindrome("race a car"));

		Assert.assertTrue(isPalindrome_("A man, a plan, a canal: Panama"));
		Assert.assertFalse(isPalindrome_("race a car"));
	}

	public static boolean isPalindrome(String s) {
		if (Strings.isNullOrEmpty(s)) {
			return true;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (Character.isLetterOrDigit(c)) {
				sb.append(Character.toLowerCase(c));
			}
		}

		StringBuilder sb2 = new StringBuilder(sb).reverse();
		return sb.toString().equals(sb2.toString());
	}

	// 双指针解法:
	public static boolean isPalindrome_(String s) {
		if (s == null) {
			return true;
		}

		throw new UnsupportedOperationException();
	}

}
