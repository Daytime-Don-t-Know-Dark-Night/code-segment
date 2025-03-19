package com.boluo.core.exception;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrentModificationExceptionTest {

	private static final Logger logger = LoggerFactory.getLogger(ConcurrentModificationExceptionTest.class);

	// https://www.jianshu.com/p/c5b52927a61a
	public static void main(String[] args) {

		// 当我们迭代一个ArrayList或者HashMap时, 如果尝试对集合做一些修改删除操作时, 可能会抛出java.util.ConcurrentModificationException异常
		// AbstractList源码: 216行, ArrayList的父类AbstractList中有一个域modCount, 每次对集合进行修改(增添元素, 删除元素...)时都会modCount++
		// 而forEach的背后实现原理就是Iterator, 在这里, 迭代ArrayList的Iterator中有一个变量expectedModCount, 该变量会初始化和modCount相等
		// 如果接下来集合进行修改(modCount改变), 就会造成 modCount != expectedModCount, 就会抛出java.util.ConcurrentModificationException异常
		func7();
	}

	public static void func1() {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");

		// 增强for循环就是在使用迭代器迭代集合
		for (String s : list) {
			if (s.equals("B")) {
				list.remove(s);
			}
		}

	}

	// TODO AbstractList源码及ArrayList源码解析
	public static void func2() {

	}

	public static void func3() {

	}

	/*
	 * 单线程情况及解决方案
	 * */
	public static void func4() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			String str = iter.next();
			if (str.equals("B")) {
				// list.add("C"); 在迭代器迭代时不能对集合进行修改, 否则会抛出java.util.ConcurrentModificationException
				// 但是可以使用iter.remove() 对集合中的数据进行remove
				iter.remove();
			}
		}
		// 这种方法只适用于单线程环境, 并且只能进行remove操作, 不支持add, clear
		System.out.println(list);
	}

	// 多线程情况
	public static void func5() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");

		new Thread(() -> {
			Iterator<String> iter = list.iterator();
			while (iter.hasNext()) {
				logger.info("{}: {}", Thread.currentThread().getName(), iter.next());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			Iterator<String> iter = list.iterator();
			while (iter.hasNext()) {
				String element = iter.next();
				logger.info("{}: {}", Thread.currentThread().getName(), element);
				if (element.equals("C")) {
					iter.remove();
				}
			}
		}).start();

		// Exception in thread "Thread-0" java.util.ConcurrentModificationException
		// 异常原因: 一个线程修改了list的modCount导致另外一个线程迭代时modCount与该迭代器的expectedModCount不相等
	}

	// 多线程解决方案1: 迭代前加锁, 但还是不能进行迭代add, clear, etc
	public static void func6() {
		List<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
		new Thread(() -> {
			Iterator<String> iter = list.iterator();
			synchronized (list) {
				while (iter.hasNext()) {
					logger.info("{}: {}", Thread.currentThread().getName(), iter.next());
					try {
						TimeUnit.MILLISECONDS.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread() {
			@Override
			public synchronized void run() {
				Iterator<String> iterator = list.iterator();
				synchronized (list) {
					while (iterator.hasNext()) {
						String e = iterator.next();
						logger.info("{}: {}", Thread.currentThread().getName(), e);
						if (e.equals("C")) {
							iterator.remove();
						}
					}
				}
			}
		}.start();
	}

	// 多线程解决方案2: 采用CopyOnWriteArrayList, 解决了多线程问题, 同时可以add, clear操作
	public static void func7() {
		// CopyOnWriteArrayList是一个线程安全的List, 实现原理: 每次add, remove等操作都是重新创建一个新的数组, 再把引用指向新的数组
		List<String> list = Lists.newCopyOnWriteArrayList(Lists.newArrayList("A", "B", "C", "D", "E"));

		new Thread(() -> {
			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				logger.info("{}: {}", Thread.currentThread().getName(), iterator.next());
				try {
					TimeUnit.MILLISECONDS.sleep(1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		new Thread() {
			public synchronized void run() {
				Iterator<String> iterator = list.iterator();
				while (iterator.hasNext()) {
					String e = iterator.next();
					logger.info("{}: {}", Thread.currentThread().getName(), e);
					if (e.equals("C")) {
						list.remove(e);
					}
				}
			}
		}.start();
	}

}
