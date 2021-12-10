package com.boluo;

import com.utils.TreeNode;
import org.junit.Assert;

/**
 * 计算给定二叉树的所有左叶子之和。
 * <p>
 * 示例：
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
 */

public class LeetCode404 {

	public static void main(String[] args) {

		TreeNode node15 = new TreeNode(15, null, null);
		TreeNode node7 = new TreeNode(7, null, null);

		TreeNode node20 = new TreeNode(20, node15, node7);
		TreeNode node9 = new TreeNode(9, null, null);

		TreeNode node3 = new TreeNode(3, node9, node20);

		int sum = sumOfLeftLeaves(node3);
		Assert.assertEquals(sum, 24);
	}

	// 深度优先搜索
	public static int sumOfLeftLeaves(TreeNode root) {
		// TODO https://leetcode-cn.com/problems/sum-of-left-leaves/solution/zuo-xie-zi-zhi-he-by-leetcode-solution/
		return root != null ? dfs(root) : 0;
	}

	public static int dfs(TreeNode node) {
		int ans = 0;
		if (node.left != null) {
			ans += isLeafNode(node.left) ? node.left.val : dfs(node.left);
		}
		if (node.right != null && !isLeafNode(node.right)) {
			ans += dfs(node.right);
		}
		return ans;
	}

	public static boolean isLeafNode(TreeNode node) {
		return node.left == null && node.right == null;
	}
}
