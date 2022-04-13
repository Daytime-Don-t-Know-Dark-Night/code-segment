package com.boluo;

import com.google.common.collect.Streams;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LeetCode1929 {

	public static void main(String[] args) {
		int[] nums = {1, 2, 1};
		int[] ans = getConcatenation(nums);
	}

	public static int[] getConcatenation(int[] nums) {

		return Stream.concat(
				StreamSupport.stream(Arrays.stream(nums).spliterator(), false),
				StreamSupport.stream(Arrays.stream(nums).spliterator(), false)
		).mapToInt(i -> i).toArray();

//		return Streams.concat(
//				Arrays.stream(nums.clone()),
//				Arrays.stream(nums.clone())
//		).toArray();

	}

}
