package com.boluo;

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 * <p>
 * 示例 1:
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 * <p>
 * 示例 2:
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 * <p>
 * 示例 3:
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 * <p>
 * 示例 4:
 * 输入: nums = [1,3,5,6], target = 0
 * 输出: 0
 * <p>
 * 示例 5:
 * 输入: nums = [1], target = 0
 * 输出: 0
 */

public class LeetCode35 {

	public static void main(String[] args) {

	}

	public static int searchInsert(int[] nums, int target) {

		// 我们要返回的值, 是target在nums中下标, 如果当前mid的值<target, 那么mid及前面的所有元素都不符合要求

		int len = nums.length;
		if (nums[len - 1] < target) {
			return len;
		}

		// 此时 nums[len - 1] >= target
		int left = 0;
		int right = len - 1;
		// 在区间[left...right]中寻找第一个大于等于target元素的下标
		while (left < right) {
			int mid = left + ((right - left) >> 1);            // 此时左边加的left就是mid及mid前面所有不符合要求的元素
			if (nums[mid] < target) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		return left;
	}
}
