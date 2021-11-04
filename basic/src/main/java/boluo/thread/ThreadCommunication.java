package boluo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadCommunication {

	private static Logger logger = LoggerFactory.getLogger(ThreadCommunication.class);

	public static void main(String[] args) {
		// https://mp.weixin.qq.com/s/bTfGYA1fSlniHieThANkzQ
		// TODO
		// 正常情况下, 每个子线程完成各自的任务就可以结束了, 但是希望多个线程协同工作来完成某个任务时, 就涉及到线程间通信

		// 两个线程分别打印123三个数字, 此时A和B是同时打印的
		// demo1();

		// 如果我们希望B在A全部打印完之后再开始打印, 可以利用thread.join()方法
		demo2();
	}

	private static void demo1() {
		Thread A = new Thread(() -> printNumber("A"));
		Thread B = new Thread(() -> printNumber("B"));
		A.start();
		B.start();
	}

	private static void demo2() {
		Thread A = new Thread(() -> printNumber("A"));
		Thread B = new Thread(() -> {
			logger.info("B开始等待A");
			try {
				A.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			printNumber("B");
		});

		B.start();
		A.start();
	}

	private static void printNumber(String threadName) {
		int i = 0;
		while (i++ < 3) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("{} -> print: {}", threadName, i);
		}
	}

}
