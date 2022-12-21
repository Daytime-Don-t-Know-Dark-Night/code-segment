package concurrent;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

	public static void main(String[] args) {

		CompletableFuture<Integer> a1 = CompletableFuture.supplyAsync(() -> func1());
		CompletableFuture<Integer> a2 = CompletableFuture.supplyAsync(() -> func2());
		CompletableFuture<Integer> a3 = CompletableFuture.supplyAsync(() -> func3());
		CompletableFuture<Integer> a4 = CompletableFuture.supplyAsync(() -> func4());

		Integer a = a1.join();
		Integer b = a1.join();
		Integer c = a1.join();
		Integer d = a1.join();

		System.out.println(a + ", " + b + ", " + c + ", " + d);
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
