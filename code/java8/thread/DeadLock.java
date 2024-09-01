package java8.thread;

import java.util.concurrent.TimeUnit;

public class DeadLock {

	public static void main(String[] args) throws InterruptedException {
		Object lockA = new Object();
		Object lockB = new Object();

		LockRunner lockRunner = new LockRunner();

		// lockRunner.lock(lockA, lockB);
		// 如果这里: 主线程lock了A, 再尝试获取B的锁, 整体只有一个主线程, 没有任何异步线程
		new Thread(() -> {
			try {
				lockRunner.lock(lockB, lockA);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		lockRunner.lock(lockA, lockB);
		// 如果这里: 发生了异步线程, 异步线程在17行的时候先拿到了B的锁, 然后等待一秒钟
		// 在等待的时候, CPU调度主线程拿到了A的锁
		// 这种情况: 主线程拥有A的锁需要B的锁, 异步线程拥有B的锁需要A的锁, 就发生了死锁
	}

	static class LockRunner {
		public void lock(Object owner, Object target) throws InterruptedException {
			synchronized (owner) {
				TimeUnit.MILLISECONDS.sleep(1000);
				synchronized (target) {
					System.out.println("success");
				}
			}
		}
	}

}
