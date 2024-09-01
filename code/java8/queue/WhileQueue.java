package java8.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class WhileQueue<T> {

	// 线程间通信
	// 在生产者-消费者模型中
	// 资源达到上限时, 生产者等待, 消费者消费
	// 资源达到下限时, 生产者生产, 消费者等待

	// 两个原本互不打扰的线程之间开始沟通, 这种线程间的调度, 就是线程间通信

	// 1. 轮询方式: 生产者和消费者线程各自使用while循环, 每隔片刻就去判断Queue的状态,
	// 队列为空时生产者才可插入数据, 队列不为空时消费者才能取出数据, 否则一律sleep等待

	private final Logger logger = LoggerFactory.getLogger(WhileQueue.class);
	private final LinkedList<T> queue = new LinkedList<>();

	public void put(T resource) throws InterruptedException {
		while (queue.size() >= 1) {
			// 队列中有数据, 不能再存入数据, 轮询等待消费者取出数据
			logger.info("生产者: 队列已满, 无法插入, 等待消费者消费...");
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		logger.info("生产者: 插入: {}!!!", resource);
		queue.addFirst(resource);
	}

	public void task() throws InterruptedException {
		while (queue.size() <= 0) {
			// 队列已空, 不能再拿取, 等待生产者生产
			logger.info("消费者: 队列为空, 无法消费, 等待生产者生产...");
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		logger.info("消费者: 取出消息!!!");
		queue.removeLast();
		TimeUnit.MILLISECONDS.sleep(5000);
	}

	public static void main(String[] args) {

		// 队列
		WhileQueue<String> queue = new WhileQueue<>();

		// 生产者
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.put("消息" + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		// 消费者
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.task();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
