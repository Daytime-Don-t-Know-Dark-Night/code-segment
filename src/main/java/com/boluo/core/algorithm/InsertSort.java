package com.boluo.core.algorithm;

import java.util.Arrays;

public class InsertSort {

	public static void main(String[] args) {
		int[] nums = {5, 9, 63, 78, 96, 51, 26, 74, 99, 13, 25, 37};
		insertSort(nums);
		Arrays.stream(nums).forEach(System.out::println);
	}

	public static void insertSort(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			for (int j = i; j > 0; j--) {
				if (nums[j - 1] > nums[j]) {
					int temp = nums[j - 1];
					nums[j - 1] = nums[j];
					nums[j] = temp;
				}
			}
		}
	}
}
