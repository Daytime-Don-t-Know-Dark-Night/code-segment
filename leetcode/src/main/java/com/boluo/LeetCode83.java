package com.boluo;

import com.utils.ListNode;

/**
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
 * 返回同样按升序排列的结果链表。
 */

public class LeetCode83 {

	public static void main(String[] args) {
		ListNode node = new ListNode(1);
		node.next = new ListNode(1);
		node.next.next = new ListNode(2);

		System.out.println(node);
	}

	public static ListNode deleteDuplicates(ListNode head) {
		throw new UnsupportedOperationException();
	}
}
