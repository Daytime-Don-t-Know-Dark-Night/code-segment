package java8.algorithm;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		int[] nums = new int[]{2, 8, 99, 15, 73, 65, 42, 37, 65};
		bubbleSort(nums);
		Arrays.stream(nums).forEach(System.out::println);
	}

	public static void bubbleSort(int[] nums) {

		boolean br;
		for (int i = 0; i < nums.length - 1; i++) {
			br = false;		// 每次循环之前, 先把br改为false
			for (int j = 0; j < nums.length - 1 - i; j++) {
				if (nums[j] > nums[j + 1]) {
					Swap.swap(nums, j, j + 1);
					br = true;		// 如果有需要交换的数据, br改为true, 如果br不改为true, 证明已经没有需要交换的数据了
				}
			}
			if (!br) {
				// 如果一轮循环已经没有需要交换的数据, 则没有再比较下去的必要了
				return;
			}
		}
	}

}
