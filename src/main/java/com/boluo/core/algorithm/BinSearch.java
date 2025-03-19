package com.boluo.core.algorithm;

import org.junit.Assert;

public class BinSearch {

	public static void main(String[] args) {
		int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		int result = binSearch1(nums, 0, 9, 8);
		Assert.assertEquals(result, 7);
	}

	// 递归写法
	public static int binSearch(int[] nums, int low, int high, int key) {
		while (low <= high) {
			int mid = (low + high) / 2;
			if (key == nums[mid]) {
				return mid;
			} else if (key < nums[mid]) {
				return binSearch(nums, low, mid - 1, key);
			} else {
				return binSearch(nums, mid + 1, high, key);
			}
		}
		return -1;
	}

	// 非递归写法
	public static int binSearch1(int[] nums, int min, int max, int key) {

		int mid = (min + max) / 2;

		while (key != nums[mid]) {
			if (key < nums[mid]) {
				max = mid - 1;
			} else {
				min = mid + 1;
			}

			if (min >= max) {
				mid = -1;
				break;
			}

			mid = (min + max) / 2;
		}
		return mid;
	}
}

