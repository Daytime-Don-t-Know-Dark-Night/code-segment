package boluo.concurrent;

import java.util.stream.Stream;

public class Synchronization2 {

	private static int n;

	public static void main(String[] args) throws InterruptedException {

		Runnable runnable = () -> Stream.iterate(0, i -> i + 1).limit(100000).forEach(i -> n++);

		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		Thread t3 = new Thread(runnable);
		Thread t4 = new Thread(runnable);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		Thread.sleep(2000);

		// 这种开启线程的方式, 主线程会等待t1线程执行完毕, 再开启2线程
		t1.start();
		t1.join();

		t2.start();
		t2.join();

		t3.start();
		t3.join();

		t4.start();
		t4.join();

		System.out.println(n);
	}

}
