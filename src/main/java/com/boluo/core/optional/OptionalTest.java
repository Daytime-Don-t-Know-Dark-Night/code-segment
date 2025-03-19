package com.boluo.core.optional;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OptionalTest {

	private static final Logger logger = LoggerFactory.getLogger(OptionalTest.class);

	public static void main(String[] args) {

		// https://www.cnblogs.com/superchong/p/11913137.html
		List<String> pageNums = Lists.newArrayList("<<", "5", "8", "1", "4", "9", "2", "3", "7", "6", ">>");

		// 取最大页码, 不使用Optional
		List<String> pageNums1 = pageNums
				.stream()
				.filter(i -> i.matches("[0-9]+"))
				.sorted((a, b) -> Integer.parseInt(b) - Integer.parseInt(a))
				.collect(Collectors.toList());

		String maxPageNum = pageNums1.isEmpty() ? "1" : pageNums1.get(0);
		logger.info(">>>>>>>" + maxPageNum);
		Assert.assertEquals(maxPageNum, "9");

		// 使用Optional
		Optional<String> maxPageNums2 = pageNums
				.stream()
				.filter(i -> i.matches("[0-9]+"))
				.max((a, b) -> Integer.parseInt(a) - Integer.parseInt(b));

		AtomicReference<String> pageNum = new AtomicReference<>("1");
		maxPageNums2.ifPresent(pageNum::set);
		logger.info(">>>>>>>" + pageNum.get());

	}

}
