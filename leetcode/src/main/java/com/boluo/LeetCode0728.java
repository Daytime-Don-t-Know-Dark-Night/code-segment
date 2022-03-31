package com.boluo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dingc
 * @Date 2022/3/31 22:12
 */
public class LeetCode0728 {

	public static void main(String[] args) {

	}

	public static List<Integer> selfDividingNumbers(int left, int right) {

		// 声明一个容器, 存储自除数
		List<Integer> res = new ArrayList<>();

		// 遍历每一个数
		for (int i = left; i <= right; i++) {
			// 判断该数是否为自除数
			String str = i + "";
			boolean br = true;
			for (char c : str.toCharArray()) {
				int num = Integer.parseInt(String.valueOf(c));
				if (num == 0 || i % num != 0) {
					br = false;
				}
			}

			// 如果该数为自除数, 加入容器中
			if (br) {
				res.add(i);
			}
		}
		return res;
	}

}
