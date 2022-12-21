package stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.function.*;
import java.util.stream.Stream;

public class FunctionalInterfaceTest {

	private static final Logger logger = LoggerFactory.getLogger(FunctionalInterfaceTest.class);

	public static void main(String[] args) {

		// https://www.orchome.com/935 *********************************************************************************

		/**
		 * @param Function<T, R>
		 * @description 接收T参数, 返回R结果
		 */
		function1("dingc", s -> s + "***");
		Stream.of("dingc").map(s -> s + "***");


		/**
		 * @param Supplier<T>
		 * @alias 生产型接口
		 * @description 不接收参数, 返回T值
		 */
		String s1 = get(() -> "dingc");
		CompletableFuture<String> s2 = CompletableFuture.supplyAsync(() -> get(() -> "dingc"));


		/**
		 * @param Consumer<T>
		 * @alias 消费型接口
		 * @description 接收T参数, 不返回值
		 */
		handle("dingc", h -> System.out.println(new StringBuilder(h).reverse()));
		handle2("dingc", h -> System.out.println(h + "***"), h -> System.out.println(h + "@@@"));


		/**
		 * @param Predicate<T>
		 * @description 接收T参数, 返回 boolean
		 */
		Stream.of("dingc").filter(i -> i.equals("qidai"));
		boolean br = filter(2021, FunctionalInterfaceTest::is_leap);


		/**
		 * @param BinaryOperator
		 * @description 接收两个T参数, 返回T对象
		 */
		Integer sum = Stream.of(1, 2, 3, 4, 5).reduce((a, b) -> a + b).get();
		Integer mySum = reduce(1, 2, (a, b) -> a + b);

		// no lambda ***************************************************************************************************

		Stream.of("dingc").map(new Function<String, Object>() {
			@Override
			public Object apply(String s) {
				return s + "***";
			}
		});

		String s3 = get(new Supplier<String>() {
			@Override
			public String get() {
				return "dingc";
			}
		});

		handle("dingc", new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(new StringBuilder(s).reverse());
			}
		});

		Stream.of("dingc").filter(new Predicate<String>() {
			@Override
			public boolean test(String s) {
				return s.equals("qidai");
			}
		});

		/**
		 * @param a 表达式执行结果的缓存
		 * @param b stream中的每一个元素
		 */
		Stream.of(1, 2, 3, 4, 5).reduce(new BinaryOperator<Integer>() {
			@Override
			public Integer apply(Integer a, Integer b) {
				System.out.printf("a = %d, b = %d %n", a, b);
				return a + b;
			}
		});
	}

	private static void function1(String s, Function<String, String> func) {
		func.apply(s);
	}

	public static <T> T get(Supplier<T> sup) {
		return sup.get();
	}

	public static void handle(String s, Consumer<String> cons) {
		cons.accept(s);
	}

	public static void handle2(String s, Consumer<String> con1, Consumer<String> con2) {
		con1.andThen(con2).accept(s);
	}

	public static boolean filter(Integer year, Predicate<Integer> pre) {
		return pre.test(year);
	}

	public static boolean is_leap(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public static <T> T reduce(T t1, T t2, BinaryOperator<T> ope) {
		return ope.apply(t1, t2);
	}

}

