package com.boluo;

import org.junit.Assert;

import java.util.Arrays;

/**
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
		Arrays.stream(edges);
		return -1;
	}
}
