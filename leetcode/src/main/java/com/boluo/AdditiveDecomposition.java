package com.boluo;

/**
 * 加法分解
 *
 * @Author dingc
 * @Date 2022/2/12 16:30
 */
public class AdditiveDecomposition {

	public static void main(String[] args) {
		sear(5, 0, "");
	}

	// 穷举所有的串
	public static void sear(int n, int cur, String str) {
		if (cur > n) // 不满足，直接跳出
			return;
		if (cur == n) { // 满足，输出结果，跳出
			System.out.println(n + "=" + str.substring(1));
			return;
		} else {
			// 递归调用，填充数字
			for (int i = 1; i <= n; i++) {
				sear(n, cur + i, str + "+" + i);
			}
		}
	}

}
