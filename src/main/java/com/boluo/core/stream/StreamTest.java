package com.boluo.core.stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamTest {

	public static void main(String[] args) {

		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("b");
		list.add("c");

		Stream<List<String>> s1 = Stream.of(list);    // 接收的参数是新流中的元素
		Stream<String> s2 = StreamSupport.stream(list.spliterator(), false);
		Stream<String> s3 = Streams.stream(list);
	}
}
