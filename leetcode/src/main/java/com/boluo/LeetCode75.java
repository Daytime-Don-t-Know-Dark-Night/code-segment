package com.boluo;

import java.util.Arrays;

/**
 * 给定一个包含红色、白色和蓝色，一共n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 此题中，我们使用整数 0、1 和 2 分别表示红色、白色和蓝色。
 * <p>
 * 示例 1：
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 * <p>
 * 示例 2：
 * 输入：nums = [2,0,1]
 * 输出：[0,1,2]
 * <p>
 * 示例 3：
 * 输入：nums = [0]
 * 输出：[0]
 * <p>
 * 示例 4：
 * 输入：nums = [1]
 * 输出：[1]
 */

public class LeetCode75 {

	public static void main(String[] args) {
		int[] nums = {2, 0, 2, 1, 1, 0};
		sortColors(nums);
		System.out.println(Arrays.toString(nums));
	}

	public static void sortColors(int[] nums) {
		// 我们只需要把0往前移,1不变,把2往后移即可
		int left = 0;    // 0的右边界
		int right = nums.length - 1;    // 2的左边界

		int index = 0;
		while (index <= right) {

			if (nums[index] == 0) {
				// 如果是0, 就往前移
				swap(nums, left, index);
				left++;
				index++;
			} else if (nums[index] == 1) {
				index++;
			} else if (nums[index] == 2) {
				// 如果是2, 就往后移
				swap(nums, right, index);
				right--;
			}

		}
	}

	// 交换数组中的两个数字
	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}

