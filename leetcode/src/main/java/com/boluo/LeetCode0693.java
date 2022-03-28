package com.boluo;

import org.junit.Assert;

/**
 * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
 * <p>
 * 示例 1：
 * 输入：n = 5
 * 输出：true
 * 解释：5 的二进制表示是：101
 * <p>
 * 示例 2：
 * 输入：n = 7
 * 输出：false
 * 解释：7 的二进制表示是：111
 * <p>
 * 示例 3：
 * 输入：n = 11
 * 输出：false
 * 解释：11 的二进制表示是：1011
 */

public class LeetCode0693 {

	public static void main(String[] args) {

		boolean res1 = hasAlternatingBits(5);
		boolean res2 = hasAlternatingBits(7);
		boolean res3 = hasAlternatingBits(11);

		Assert.assertTrue(res1);
		Assert.assertFalse(res2);
		Assert.assertFalse(res3);
	}

	public static boolean hasAlternatingBits(int n) {
		// 十进制转二进制
		String str = Integer.toBinaryString(n);

		for (int i = 1; i < str.length(); i++) {
			int j = i - 1;
			if (str.charAt(i) == str.charAt(j)) {
				return false;
			}
		}

		return true;
	}
}
