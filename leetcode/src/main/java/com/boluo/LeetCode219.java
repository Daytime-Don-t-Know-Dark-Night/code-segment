package com.boluo;

import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，
 * 并且 i 和 j 的差的绝对值至多为 k。
 * <p>
 * 示例 1:
 * 输入: nums = [1,2,3,1], k = 3
 * 输出: true
 * <p>
 * 示例 2:
 * 输入: nums = [1,0,1,1], k = 1
 * 输出: true
 * <p>
 * 示例 3:
 * 输入: nums = [1,2,3,1,2,3], k = 2
 * 输出: false
 */

public class LeetCode219 {

	public static void main(String[] args) {

		int[] nums1 = {1, 2, 3, 1};
		int[] nums2 = {1, 0, 1, 1};
		int[] nums3 = {1, 2, 3, 1, 2, 3};

		Assert.assertTrue(containsNearbyDuplicate_(nums1, 3));
		Assert.assertTrue(containsNearbyDuplicate_(nums2, 1));
		Assert.assertFalse(containsNearbyDuplicate_(nums3, 2));

	}

	// 超时
	public static boolean containsNearbyDuplicate(int[] nums, int k) {
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] == nums[j] && Math.abs(i - j) <= k) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsNearbyDuplicate_(int[] nums, int k) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < nums.length; i++) {
			// 在散列表中搜索当前元素, 如果找到了就返回true
			if (set.contains(nums[i])) {
				return true;
			}
			// 没找到的话, 在散列表中添加该元素
			set.add(nums[i]);
			// 如果当前散列表的大小超过了k, 删除散列表中最旧的元素
			if (set.size() > k) {
				System.out.println(i - k);
				set.remove(nums[i - k]);
			}
		}
		return false;
	}
}
