package iterable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

public class ListIteratorTest {

	private static final Logger logger = LoggerFactory.getLogger(ListIteratorTest.class);

	public static void main(String[] args) {

		// 普通的迭代器不能逆序遍历, ListIterator可以
		List<Integer> list = new ArrayList<>();
		Stream.iterate(0, i -> i + 1).limit(10).forEach(list::add);
		System.out.println(list);

		ListIterator<Integer> it = list.listIterator(list.size());
		while (it.hasPrevious()) {
			Integer pre = it.previous();
			if (pre % 2 == 0) {
				logger.info("remove: " + pre);
				it.remove();
			}
		}

		System.out.println(list);
	}
}
