package com.boluo.core.algorithm;

public class Swap {

	public static void swap(int[] nums, int a, int b) {
		int temp = nums[a];
		nums[a] = nums[b];
		nums[b] = temp;
	}

	public static void swap1(int a, int b) {
		a = a + b;
		b = a - b;
		a = a - b;
	}

	public static void swap2(int a, int b) {
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
	}
}
