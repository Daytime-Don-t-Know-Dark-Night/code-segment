package java8.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnableTest {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("使用线程池运行Runnable任务: ");

		// 创建大小固定为5的线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		List<AccumRunnable> tasks = new ArrayList<>(10);

		for (int i = 0; i < 10; i++) {
			AccumRunnable task = new AccumRunnable(i * 10 + 1, (i + 1) * 10);
			tasks.add(task);

			// 使用线程池执行任务task
			threadPool.execute(task);
		}

		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.HOURS);

		int total = 0;
		for (AccumRunnable task : tasks) {
			total += task.getResult();
		}

		System.out.println("Total: " + total);
	}

	static final class AccumRunnable implements Runnable {

		private final int begin;
		private final int end;
		private int result;

		public AccumRunnable(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}

		@Override
		public void run() {
			result = 0;
			for (int i = begin; i <= end; i++) {
				result += i;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace(System.err);
				}
				System.out.printf("(%s) - 运行结束, 结果为 %d\n",
						Thread.currentThread().getName(), result);
			}
		}

		public int getResult() {
			return result;
		}
	}

}
