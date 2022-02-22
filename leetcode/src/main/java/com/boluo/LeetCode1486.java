package com.boluo;

import org.junit.Assert;

/**
 * 数组异或操作
 * <p>
 * 给你两个整数，n 和 start 。
 * 数组 nums 定义为：nums[i] = start + 2*i（下标从 0 开始）且 n == nums.length 。
 * 请返回 nums 中所有元素按位异或（XOR）后得到的结果。
 * <p>
 * <p>
 * 示例 1：
 * 输入：n = 5, start = 0
 * 输出：8
 * 解释：数组 nums 为 [0, 2, 4, 6, 8]，其中 (0 ^ 2 ^ 4 ^ 6 ^ 8) = 8 。   "^" 为按位异或 XOR 运算符。
 * <p>
 * 示例 2：
 * 输入：n = 4, start = 3
 * 输出：8
 * 解释：数组 nums 为 [3, 5, 7, 9]，其中 (3 ^ 5 ^ 7 ^ 9) = 8.
 * <p>
 * 示例 3：
 * 输入：n = 1, start = 7
 * 输出：7
 * <p>
 * 示例 4：
 * 输入：n = 10, start = 5
 * 输出：2
 */
public class LeetCode1486 {

	public static void main(String[] args) {
		int res1 = xorOperation(5, 0);
		int res2 = xorOperation(4, 3);
		int res3 = xorOperation(1, 7);
		int res4 = xorOperation(10, 5);

		Assert.assertEquals(8, res1);
		Assert.assertEquals(8, res2);
		Assert.assertEquals(7, res3);
		Assert.assertEquals(2, res4);
	}

	public static int xorOperation(int n, int start) {
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum ^= start + 2 * i;
		}
		return sum;
	}

}
