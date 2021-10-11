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

		deleteDuplicates(node);
		System.out.println(node);
	}

	public static ListNode deleteDuplicates(ListNode head) {

		if (head == null) {
			return null;
		}

		// 我们将指针curr指向链表的头结点, 随后对链表进行遍历
		ListNode curr = head;
		while (curr.next != null) {
			// 如果当前节点元素与下一节点元素相同, 则将下一节点元素删除
			if (curr.val == curr.next.val) {
				curr.next = curr.next.next;
			} else {
				curr = curr.next;
			}
		}

		return head;
	}
}
