package com.boluo.algorithm;

import java.util.Arrays;

public class SelectSort {

    public static void main(String[] args) {
        int nums[] = new int[]{2, 8, 99, 15, 73, 65, 42, 37, 65};
        selectSort(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }

    public static void selectSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    Swap.swap(nums, i, j);
                }
            }
        }
    }

}
