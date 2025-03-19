package com.boluo.core.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericSymbol<T> {

	/**
	 * @param args
	 * @generic https://www.jb51.net/article/189936.htm
	 */
	public static void main(String[] args) {

		// Java的泛型是伪泛型, 即在Java编译期间, 所有的泛型信息都会被擦除(类型擦除)
		// 泛型是通过类型擦除来实现的, 编译器在编译时擦除了所有泛型类型相关的信息, 所以在运行时不存在任何泛型类型相关的信息
		// List<Integer>在运行时仅用一个List来表示

		// 1. <T> T和T的区别: T是Type的缩写, <T> T表示返回值是一个泛型, 传入什么类型, 就返回什么类型
		// 而单独的T表示限制传入的类型参数

		GenericSymbol<String> gs = new GenericSymbol<String>();
		List<String> array = new ArrayList<String>();
		array.add("dingc");
		array.add("boluo");

		String str = gs.getListFirst(array);
		System.out.println(str);


		GenericSymbol<Integer> gs2 = new GenericSymbol<Integer>();
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(1);
		nums.add(2);
		Integer num = gs2.getListFirst(nums);
		System.out.println(num);
	}

	/**
	 * @param data
	 * @param <T>  限制参数类型为T
	 * @return <T>表示返回值是一个泛型, T是一个占位符, 到编译的时候再告诉计算机是什么类型
	 */
	private <T> T getListFirst(List<T> data) {
		if (data.isEmpty()) {
			return null;
		}
		return data.get(0);
	}


}
