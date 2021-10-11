package com.boluo;

import org.junit.Assert;

/**
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度。
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 * <p>
 * 示例 1：
 * 输入：s = "Hello World"
 * 输出：5
 * <p>
 * 示例 2：
 * 输入：s = "   fly me   to   the moon  "
 * 输出：4
 * <p>
 * 示例 3：
 * 输入：s = "luffy is still joyboy"
 * 输出：6
 * <p>
 * <p>
 * 提示：
 * 1 <= s.length <= 104
 * s 仅有英文字母和空格 ' ' 组成
 * s 中至少存在一个单词
 */

public class LeetCode58 {

	public static void main(String[] args) {

		int r1 = lengthOfLastWord("Hello World");
		Assert.assertEquals(r1, 5);

		int r2 = lengthOfLastWord("   fly me   to   the moon  ");
		Assert.assertEquals(r2, 4);

		int r3 = lengthOfLastWord("luffy is still joyboy");
		Assert.assertEquals(r3, 6);

		int r4 = lengthOfLastWord("a");
		Assert.assertEquals(r4, 1);
	}

	public static int lengthOfLastWord(String s) {

		int letterIdx = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c != ' ') {
				break;
			}
			letterIdx++;
		}

		// 第一个字母的位置(倒数), s.length() - letterIdx
		int len = 0;
		for (int i = s.length() - letterIdx - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c == ' ') {
				return len;
			}
			len++;
		}
		return len;
	}
}
