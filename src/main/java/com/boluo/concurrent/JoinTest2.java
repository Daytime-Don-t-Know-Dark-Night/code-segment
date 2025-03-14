package com.boluo.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinTest2 {

	// join 与sleep(不释放锁), wait(释放锁)都属于多线程运行控制常用方法

	// join的作用类似于排队, 比如线程A调用了线程B的join方法, 则A会进入阻塞状态, B会一直运行, 直到B结束后A才会继续执行
	// 比如主线程需要等待子线程的运行结果进行汇总, 如果主线程先于子线程运行完成, 结果就会有误差, 所以需要在主线程中调用子线程的join方法, 这样主线程就会等等待子线程运行完成后才会运行

	private static final Logger logger = LoggerFactory.getLogger(JoinTest2.class);

	public static void main(String[] args) throws InterruptedException {

		logger.info("main线程开始执行...");

		Child t1 = new Child();
		t1.start();
		t1.join();

		System.out.println("main线程执行完毕...");
	}

}

class Child extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(Child.class);

	@Override
	public void run() {

		int sleepTime = (int) (Math.random() * 1000);
		try {
			logger.info("child线程准备执行...");
			Thread.sleep(sleepTime);
			logger.info("child线程执行完毕...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
