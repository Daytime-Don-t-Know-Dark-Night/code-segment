package com.boluo;

import org.junit.Assert;

/**
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * <p>
 * 示例 1：
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * <p>
 * 示例 2：
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * <p>
 * 示例 3：
 * 输入：digits = [0]
 * 输出：[1]
 * <p>
 * 提示：
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */

public class LeetCode66 {

	public static void main(String[] args) {

		int[] r1 = plusOne(new int[]{1, 2, 3});
		Assert.assertEquals(r1.length, 3);
		Assert.assertEquals(r1[0], 1);
		Assert.assertEquals(r1[1], 2);
		Assert.assertEquals(r1[2], 4);

		int[] r2 = plusOne(new int[]{4, 3, 2, 9});
		Assert.assertEquals(r2.length, 4);
		Assert.assertEquals(r2[0], 4);
		Assert.assertEquals(r2[1], 3);
		Assert.assertEquals(r2[2], 3);
		Assert.assertEquals(r2[3], 0);

		int[] r3 = plusOne(new int[]{9, 9, 9});
		Assert.assertEquals(r3.length, 4);
		Assert.assertEquals(r3[0], 1);
		Assert.assertEquals(r3[1], 0);
		Assert.assertEquals(r3[2], 0);
		Assert.assertEquals(r3[3], 0);
	}

	public static int[] plusOne(int[] digits) {
		throw new UnsupportedOperationException();
	}
}
