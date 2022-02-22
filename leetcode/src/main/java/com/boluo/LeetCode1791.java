package com.boluo;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 找出星型图的中心节点
 * <p>
 * 有一个无向的星型图，由n个编号从1到n的节点组成。星型图有一个中心节点，并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
 * 给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。请你找出并返回 edges 所表示星型图的中心节点。
 * <p>
 * 示例 1：
 * 输入：edges = [[1,2],[2,3],[4,2]]
 * 输出：2
 * 解释：如上图所示，节点 2 与其他每个节点都相连，所以节点 2 是中心节点。
 * <p>
 * 示例 2：
 * 输入：edges = [[1,2],[5,1],[1,3],[1,4]]
 * 输出：1
 */
public class LeetCode1791 {

	public static void main(String[] args) {
		findTest();
		findCenterTest();
	}

	public static void findCenterTest() {

		int[][] nums1 = new int[][]{
				{1, 2},
				{2, 3},
				{4, 2}
		};

		int[][] nums2 = new int[][]{
				{1, 2},
				{5, 1},
				{1, 3},
				{1, 4}
		};

		Assert.assertEquals(2, findCenter(nums1));
		Assert.assertEquals(1, findCenter(nums2));
	}

	public static int findCenter(int[][] edges) {
		List<List<Integer>> list = new ArrayList<>();
		Arrays.stream(edges).forEach(i -> {
			List<Integer> inner = Arrays.stream(i).boxed().collect(Collectors.toList());
			list.add(inner);
		});
		List<Integer> res = list.stream().reduce((l1, l2) -> {
			l1.retainAll(l2);
			return l1;
		}).orElse(Collections.emptyList());
		Optional<Integer> opt = res.stream().findFirst();
		Preconditions.checkArgument(opt.isPresent());
		return opt.get();
	}

	public static void findTest() {
		int[] nums1 = {1, 2};
		int[] nums2 = {5, 1};
		int[] nums3 = {1, 3};
		int[] nums4 = {1, 4};
		int res = find(nums1, nums2, nums3, nums4);
		Assert.assertEquals(1, res);
	}

	public static int find(int[]... nums) {

		List<List<Integer>> list = Lists.newArrayList();
		Arrays.stream(nums).forEach(i -> {
			List<Integer> inner = Arrays.stream(i).boxed().collect(Collectors.toList());
			list.add(inner);
		});

		List<Integer> res = list.stream().reduce((l1, l2) -> {
			l1.retainAll(l2);
			return l1;
		}).orElse(Collections.emptyList());

		Optional<Integer> opt = res.stream().findFirst();
		Preconditions.checkArgument(opt.isPresent());
		return opt.get();

	}

}
