package com.boluo.core.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class Bloom {

	// 布隆过滤器
	public static void main(String[] args) {

		// 参数1: 插入数据的类型, 参数2: 预计插入多少条数据, 参数3: 期望的误判率
		BloomFilter<Integer> integerBloomFilter =
				BloomFilter.create(Funnels.integerFunnel(), 1000000, 0.001);

		for (int i = 0; i < 1000000; i++) {
			integerBloomFilter.put(i);
		}

		System.out.println(integerBloomFilter.mightContain(666));

		// 验证下1000000条数据中有多少条数据判断错误
		int count = 0;
		for (int i = 1000000; i < 2000000; i++) {
			if (integerBloomFilter.mightContain(i)) {
				count++;
			}
		}

		System.out.println(String.format("1000000条数据中误差的数据有%d条", count));
	}

}
