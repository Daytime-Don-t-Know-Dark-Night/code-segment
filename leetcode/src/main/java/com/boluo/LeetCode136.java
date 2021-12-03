package com.boluo;

import org.junit.Assert;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 说明：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 示例 1:
 * 输入: [2,2,1]
 * 输出: 1
 * <p>
 * 示例2:
 * 输入: [4,1,2,1,2]
 * 输出: 4
 */

public class LeetCode136 {

	public static void main(String[] args) {
		// 异或, 参与运算的两个值, 如果两个相应的bit位相同, 则结果为0, 否则为1
		// 0 ^ 0 = 0
		// 0 ^ 1 = 1
		// 1 ^ 0 = 1
		// 1 ^ 1 = 0

		int res = singleNumber(new int[]{2, 2, 1});
		Assert.assertEquals(res, 1);
	}

	public static int singleNumber(int[] nums) {
		// 所有的数字异或起来就是答案
		// 异或的性质:
		// 1.任何数和0做异或运算, 结果仍然是原来的数, 即 a ^ 0 = a
		// 2.任何数和其自身做异或运算, 结果是0, 即 a ^ a = 0
		// 3.异或运算满足交换律和结合律, 即 a ^ b ^ a = b ^ a ^ a = b ^ (a ^ a) = b ^ 0 = b

		// 所以所有的数字异或起来, 就相当于唯一的数字 a ^ 0 ^ 0 ^ 0 ... = a
		int single = 0;
		for (int num : nums) {
			single ^= num;
		}
		return single;
	}
}
