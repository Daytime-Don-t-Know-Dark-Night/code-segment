package concurrent;

import java.util.ArrayList;
import java.util.List;

public class Thread1 {

	public static void main(String[] args) throws InterruptedException {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("线程启动...");
				// 耗时操作
				System.out.println("线程结束...");
			}
		};

		Thread thread = new Thread(runnable);        // 创建线程, runnable作为线程要执行的任务
		thread.start();        // 启动线程
		thread.join();        // 等待线程执行完毕


		/***************************************************************************************/

		// Runnable的run方法是不带返回值的, 如果需要返回值
		// 则在Runnable的实现类中设置一个变量V, 在run方法中改变V的值, 然后通过getV的方法返回该变量

		System.out.println("使用Runnable获得返回结果");

		List<Thread> workers = new ArrayList<>(10);
		List<AccumRunnable> tasks = new ArrayList<>(10);

		// 新建10个线程, 每个线程分别负责累加 1-10, 11-20, ... 91-100
		for (int i = 0; i < 10; i++) {
			AccumRunnable task = new AccumRunnable(i * 10 + 1, (i + 1) * 10);
			Thread worker = new Thread(task, "慢速累加器线程" + i);

			tasks.add(task);
			workers.add(worker);

			worker.start();
		}

		int total = 0;
		for (int i = 0, s = workers.size(); i < s; i++) {
			workers.get(i).join();    // 等待线程执行完毕
			total += tasks.get(i).getResult();
		}

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
			try {
				for (int i = begin; i <= end; i++) {
					result += i;
					Thread.sleep(100);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace(System.err);
			}
			System.out.printf("(%s) - 运行结束，结果为 %d\n", Thread.currentThread().getName(), result);
		}

		public int getResult() {
			return result;
		}
	}


}
