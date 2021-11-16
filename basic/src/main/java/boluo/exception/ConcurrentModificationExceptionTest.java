package boluo.exception;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentModificationExceptionTest {

	// https://www.jianshu.com/p/c5b52927a61a
	public static void main(String[] args) {

		// 当我们迭代一个ArrayList或者HashMap时, 如果尝试对集合做一些修改删除操作时, 可能会抛出java.util.ConcurrentModificationException异常
		// AbstractList源码: 216行, ArrayList的父类AbstractList中有一个域modCount, 每次对集合进行修改(增添元素, 删除元素...)时都会modCount++
		// 而forEach的背后实现原理就是Iterator, 在这里, 迭代ArrayList的Iterator中有一个变量expectedModCount, 该变量会初始化和modCount相等
		// 如果接下来集合进行修改(modCount改变), 就会造成 modCount != expectedModCount, 就会抛出java.util.ConcurrentModificationException异常
		func1();

	}

	public static void func1() {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");

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
}
