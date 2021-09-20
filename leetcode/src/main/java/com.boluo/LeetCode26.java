package com.boluo;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Objects;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 * <p>
 * <p>
 * 示例 2：
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 */

public class LeetCode26 {

	public static void main(String[] args) {
		int[] nums1 = {1, 1, 2};
		int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};

		System.out.println(removeDuplicates(nums1));
		System.out.println(removeDuplicates(nums2));
	}

	public static int removeDuplicates(int[] nums) {

		// 双指针
		if (Objects.isNull(nums) || nums.length == 0) {
			return 0;
		}

		// 数组是有序的, 那么重复的元素一定相邻, 删除重复元素, 返回新数组长度, 即将不重复的元素移到数组左侧
		// 使用两个指针, 一个在前p, 一个在后q

		// p q
		// 0 0 1 1 1 2 2 3 3 4
		int p = 0;
		int q = 1;
		while (q < nums.length) {

			// 先比较p, q位置上的元素是否相同
			if (nums[p] != nums[q]) {

				// 如果不相等, 将q位置上的元素复制到p+1位置上, p,q都往后
				nums[p + 1] = nums[q];
				p++;
			}
			q++;

			// 重复上述过程, 知道q等于数组长度
		}

		return p + 1;
	}
}
