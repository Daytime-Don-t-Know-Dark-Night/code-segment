package com.boluo;

import org.junit.Assert;

import java.util.*;

/**
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
 * <p>
 * 示例 1:
 * 输入: [1,2,3,1]
 * 输出: true
 * <p>
 * 示例 2:
 * 输入: [1,2,3,4]
 * 输出: false
 * <p>
 * 示例 3:
 * 输入: [1,1,1,3,3,4,3,2,4,2]
 * 输出: true
 */

public class LeetCode217 {

	public static void main(String[] args) {

		int[] nums1 = {1, 2, 3, 1};
		int[] nums2 = {1, 2, 3, 4};
		int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};

		Assert.assertTrue(containsDuplicate(nums1));
		Assert.assertFalse(containsDuplicate(nums2));
		Assert.assertTrue(containsDuplicate(nums3));
	}

	// 超时
	public static boolean containsDuplicate(int[] nums) {

		List<Integer> list = new ArrayList<>();
		for (int i : nums) {
			if (list.contains(i)) {
				return true;
			}
			list.add(i);
		}
		return false;
	}

	public static boolean containsDuplicate_(int[] nums) {

		Set<Integer> set = new HashSet<Integer>();
		for (int x : nums) {
			if (!set.add(x)) {
				return true;
			}
		}
		return false;
	}
}
