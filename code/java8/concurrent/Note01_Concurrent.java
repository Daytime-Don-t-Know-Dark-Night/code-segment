package java8.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Note01_Concurrent {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 如果是无返回值任务的调用, 可以用execute或者submit方法, 这种情况下二者本质上一样
		// 为了与有返回值任务调用保持统一, 建议采用submit方法

		// 创建一个线程池
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		// 提交一个无返回值的任务(实现了Runnable接口)
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello");
			}
		});

		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

		/**********************************************************************/

		// 如果有一个任务集合, 可以一个个提交

		// 创建一个线程池
		ExecutorService executorService1 = Executors.newFixedThreadPool(3);
		List<Runnable> tasks = Arrays.asList(
				() -> System.out.println("hello"),
				() -> System.out.println("world"));

		// 逐个提交任务
		// tasks.forEach(executorService1::submit);
		for (Runnable task : tasks) {
			executorService1.submit(task);
		}

		executorService1.shutdown();
		executorService1.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);


		/******************************************************************************************/


		// 有返回值任务的调用
		// 有返回值的任务需要实现Callable接口, 实现的时候在泛型位置指定返回值类型, 在调用submit方法时会返回一个Future对象
		// 通过Future的get()方法可以拿到返回值
		// 注意: 调用get()方法的时候代码会阻塞, 直到任务完成, 有返回值

		ExecutorService executorService2 = Executors.newFixedThreadPool(2);
		Future<String> future = executorService2.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "dingc";
			}
		});

		System.out.println(future.isDone());
		String value = future.get();
		System.out.println(future.isDone());


		// 如果要提交一批任务, 可以使用invokeAll一次提交
		ExecutorService executorService3 = Executors.newFixedThreadPool(2);
		List<Callable<String>> tasks_ = Arrays.asList(() -> "dingc", () -> "boluo");
		List<Future<String>> futures = executorService3.invokeAll(tasks_);

		// invokeAny(): 线程池执行若干个是实现了Callable的任务, 然后返回最先执行结束的任务的值, 其他未完成的任务将被正常取消掉不会有异常
		ExecutorService executorService4 = Executors.newFixedThreadPool(2);
		List<Callable<String>> tasks__ = Arrays.asList(
				() -> {
					Thread.sleep(500);
					System.out.println("Hello");
					return "hello";
				}, () -> {
					System.out.println("World");
					return "world";
				});
		String s = executorService4.invokeAny(tasks__);

		// submit(): 可以通过这个方法提交一个实现了Runnable接口的任务, 然后有返回值, 而Runnable接口中的run方法是没有返回值的
		ExecutorService executorService5 = Executors.newFixedThreadPool(2);
		Future<String> fu = executorService5.submit(() -> "boluo");
		fu.get();
	}

}
