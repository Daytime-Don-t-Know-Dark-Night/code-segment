package com.boluo;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Compose {

	public static void main(String[] args) {

		// 对下面的数字分组, 要求每组的数字之和 <= 10, 尽可能分最少的组
		int[] nums = {5, 8, 2, 6, 4, 7, 5, 9, 2, 1, 3, 4, 6, 7, 2, 6, 1, 2, 5, 7, 3, 9};

		List<List<Integer>> res = func(nums);
		System.out.println(res);
	}

	public static List<List<Integer>> func(int[] nums) {

		// 将数字从大到小排序
		Integer[] ints = Arrays.stream(nums).boxed().sorted(Comparator.comparing(Integer::intValue).reversed()).toArray(Integer[]::new);
		// 9, 9, 8, 7, 7, 7, 6, 6, 6, 5, 5, 5, 4, 4, 3, 3, 2, 2, 2, 2, 1, 1
		// 0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21

		// 维护第二个指针
		int j_ = ints.length - 1;

		// 从左往右累加, 超过10放弃最后一次累加, 转而从最后(最小的数)往前累加, 超过10放弃最后一次累加, 保留剩余数字组合
		List<List<Integer>> res = Lists.newArrayList();
		List<Integer> tmp = Lists.newArrayList();
		for (int i = 0; i <= j_; i++) {
			tmp.add(ints[i]);
			int sum = tmp.stream().mapToInt(in -> in).sum();

			// 如果循环到最后一次但是sum < 10 或者 sum == 10, 作为一次组合返回
			if (i == j_ && sum < 10 || sum == 10) {
				res.add(Lists.newArrayList(tmp));
				tmp.clear();
				continue;
			}

			// 如果累加的sum > 10, 舍弃这次累加, 转而从后(最小的数字)往前累加
			if (sum > 10) {

				i--;
				tmp.remove(tmp.size() - 1);

				for (int j = j_; j > i; j--) {
					tmp.add(ints[j]);
					int sum_ = tmp.stream().mapToInt(in -> in).sum();
					if (sum_ < 10) {
						j_--;
					}
					if (sum_ == 10) {
						res.add(Lists.newArrayList(tmp));
						j_--;
						tmp.clear();
						break;
					}
					if (sum_ > 10) {
						// 舍弃这次累加
						tmp.remove(tmp.size() - 1);
						res.add(Lists.newArrayList(tmp));
						tmp.clear();
						break;
					}
				}
			}
		}
		return res;
	}

}




