package java8.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Uri https://www.jianshu.com/p/2086154ae5cb
 */
public class CompletableFuture1 {

	@Test
	public void func1() throws InterruptedException, ExecutionException {
		Future<String> completableFuture = calculateAsync();
		String result = completableFuture.get();
		Assert.assertEquals(result, "Hello");
	}

	@Test
	public void func2() throws ExecutionException, InterruptedException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		Assert.assertEquals(future.get(), "Hello");
	}

	@Test
	// 处理异步计算的结果
	public void func3() throws ExecutionException, InterruptedException {

		// 处理计算结果的最通用方法是将其提供给函数, thenApply方法正是这么做的: 接受一个函数实例, 用它来处理结果, 并返回一个未来的保存函数的返回值
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");
		Assert.assertEquals("Hello World", future.get());
	}

	@Test
	public void func4() throws ExecutionException, InterruptedException {
		// 如果不需要在Future链中返回值, 则可以使用Consumer功能接口的实例, 他的单个方法接受一个参数返回void
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<Void> future = completableFuture.thenAccept(s -> System.out.println("Computation returned: " + s));
		future.get();
	}

	@Test
	public void func5() throws ExecutionException, InterruptedException {
		// 如果既不需要计算的值也不想在链的末尾返回一些值, 那么可以将Runnable lambda传递给thenRun方法
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<Void> future = completableFuture.thenRun(() -> System.out.println("Computation finished."));
		future.get();
	}

	@Test
	public void func6() {
		// CompletableFuture能够在一系列计算步骤中组合CompletableFuture实例
		// 这种链接的结果本身就是CompletableFuture, 允许进一步链接和组合(monadic设计模式)
		// 我们使用thenCompose方法顺序链接两个Futures



	}

	public static Future<String> calculateAsync() {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();

		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(500);
			completableFuture.complete("Hello");
			return null;
		});

		return completableFuture;
	}

}
