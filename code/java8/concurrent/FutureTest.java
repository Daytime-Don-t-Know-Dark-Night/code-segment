package java8.concurrent;

import java.util.concurrent.*;

public class FutureTest {

	private static final ExecutorService executor = Executors.newCachedThreadPool();

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		Future<Integer> a = executor.submit(() -> func1());
		Future<Integer> b = executor.submit(() -> func2());
		Future<Integer> c = executor.submit(() -> func3());
		Future<Integer> d = executor.submit(() -> func4());

		System.out.println(a.get());
		System.out.println(b.get());
		System.out.println(c.get());
		System.out.println(d.get());

	}

	public static int func1() {
		int sum = 0;
		for (int i = 1; i <= 10000; i++) {
			sum += i;
			System.out.println("AAA---");
		}
		return sum;
	}

	public static int func2() {
		int sum = 0;
		for (int i = 1; i <= 10000; i++) {
			sum += i;
			System.out.println("BBB---");
		}
		return sum;
	}

	public static int func3() {
		int sum = 0;
		for (int i = 1; i <= 10000; i++) {
			sum += i;
			System.out.println("CCC---");
		}
		return sum;
	}

	public static int func4() {
		int sum = 0;
		for (int i = 1; i <= 10000; i++) {
			sum += i;
			System.out.println("DDD---");
		}
		return sum;
	}
}
