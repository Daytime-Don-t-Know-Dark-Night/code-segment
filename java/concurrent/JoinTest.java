package concurrent;

/**
 * join方法主要是用于将当前线程挂起, 等待其他线程结束后再执行当前线程
 * <p>
 * 比如有三个人小红、小李、小王， 三个人相约一起去酒店吃饭，菜已经点好了，
 * 三个人从不同的地方出发，只有三个人都到了酒店之后才会开始上菜；
 * 那么这三个人就分别代表三个线程，这三个线程执行完之后才会执行 “上菜” 的代码逻辑，
 */
public class JoinTest implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "开始出发了...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "到酒店了...");
	}
}

class Hotel implements Runnable {

	Thread thread;

	public Hotel(Thread thread) {
		this.thread = thread;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "正在等待大家的到来...");
		try {
			// 等待其他线程执行完成后再执行下面的代码
			// join实现线程等待是调用了wait方法
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("人齐了, " + Thread.currentThread().getName() + "开始上菜...");
	}
}

class Test {

	public static void main(String[] args) {

		Thread t1 = new Thread(new JoinTest(), "小李");
		Thread t2 = new Thread(new JoinTest(), "小丁");
		Thread t3 = new Thread(new JoinTest(), "小崔");

		// 三个人同时出发
		t1.start();
		t2.start();
		t3.start();

		// 酒店开始准备
		new Thread(new Hotel(t3), "酒店").start();
	}

}