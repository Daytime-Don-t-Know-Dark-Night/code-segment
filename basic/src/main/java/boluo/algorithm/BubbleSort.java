package boluo.algorithm;

import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		int[] nums = new int[]{2, 8, 99, 15, 73, 65, 42, 37, 65};
		bubbleSort(nums);
		Arrays.stream(nums).forEach(System.out::println);
	}

	public static void bubbleSort(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = 0; j < nums.length - 1 - i; j++) {
				if (nums[j] > nums[j + 1]) {
					Swap.swap(nums, j, j + 1);
				}
			}
		}
	}

}
