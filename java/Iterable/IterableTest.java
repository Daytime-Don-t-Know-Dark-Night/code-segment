package iterable;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterableTest {

	private static final Logger logger = LoggerFactory.getLogger(IterableTest.class);

	public static void main(String[] args) {

		Iterable<String> iterable = Lists.newArrayList("a", "b", "c");

		// Iterable转Stream的方法
		// false 代表不使用并行流
		Stream<String> stream1 = StreamSupport.stream(iterable.spliterator(), false);
		Stream<String> stream2 = Streams.stream(iterable);

		logger.info("size: " + Iterables.size(iterable));
		logger.info("1: " + Iterables.get(iterable, 0));
		logger.info("1: " + Iterables.get(iterable, 1));
		logger.info("1: " + Iterables.get(iterable, 2));

	}
}
