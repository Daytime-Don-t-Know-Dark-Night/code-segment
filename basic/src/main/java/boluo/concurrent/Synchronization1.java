package boluo.concurrent;

import java.util.concurrent.CompletableFuture;

public class Synchronization1 {

	private static int n;

	public static void main(String[] args) {

		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> func1());
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> func1());
		CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync(Synchronization1::func1);
		CompletableFuture<Integer> f4 = CompletableFuture.supplyAsync(Synchronization1::func1);

		int i1 = f1.join();
		int i2 = f2.join();
		int i3 = f3.join();
		int i4 = f4.join();

		System.out.println("i1 = " + i1);
		System.out.println("i2 = " + i2);
		System.out.println("i3 = " + i3);
		System.out.println("i4 = " + i4);
	}

	public static int func1() {
		for (int i = 0; i < 100000; i++) {
			n++;
		}
		return n;
	}
}
