package generic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Generic<T> {

	private static final Logger logger = LoggerFactory.getLogger(Generic.class);

	// TODO https://juejin.cn/post/6960913691990556680
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Generic1<?> generic = new Generic1<>();
		generic.func6();
	}

	@Test
	public void func1() {
		// 在Java中, 泛型只在编译阶段有效
		List<String> stringArrayList = new ArrayList<>();
		List<Integer> integerArrayList = new ArrayList<>();

		Class classStringArrayList = stringArrayList.getClass();
		Class classIntegerArrayList = integerArrayList.getClass();

		// 在编译之后程序会采取去泛型化的措施, 也就是说, Java中的泛型, 只在编译阶段有效
		// 泛型信息不会进入到运行时阶段
		if (classStringArrayList.equals(classIntegerArrayList)) {
			logger.info("泛型测试, 类型相同!");
		}
	}

	// 泛型有三种使用方式: 泛型类, 泛型接口, 泛型方法
	// 一个最普通的泛型类:
	public static class Generic1<T> {    // 这里T可以随便写为任意标识, 常见的如T, E, K, V等
		// key这个成员变量的类型为T, T的类型由外部指定
		private T key;

		public Generic1() {

		}

		public Generic1(T key) {        // 泛型构造方法形参key的类型也为T, T的类型由外部指定
			this.key = key;
		}

		public T getKey() {        // 泛型方法getKey的返回值类型为T, T的类型由外部指定
			return key;
		}

		// 泛型通配符
		public void func1(Generic1<Number> obj) {
			logger.info("泛型测试, key value is : {}", obj.getKey());
		}

		public void func2() {
			Generic1<Integer> gInteger = new Generic1<Integer>(123);
			Generic1<Number> gNumber = new Generic1<Number>(456);
			// 传入gInteger会报错, 由此可以看出Generic<Integer>不能被看作Generic<Number>的子类
			// 因此: 同一种泛型可以对应多个版本(因为参数类型不确定), 不同版本的泛型类实例是不兼容的
			func1(gNumber);
		}

		// 泛型通配符
		public void func3(Generic1<?> obj) {
			// 此处的?一个实参, 而不是形参, 可以把?看作是所有类型的父类, 是一种真实的类型
			// 可以用 ? 来表示未知类型
			logger.info("泛型测试, key value is : {}", obj.getKey());
		}

		public void func4() {
			Generic1<Integer> gInteger = new Generic1<Integer>(123);
			Generic1<Number> gNumber = new Generic1<Number>(456);
			func3(gInteger);
			func3(gNumber);
		}

		// 泛型类: 是在实例化类的时候指明泛型的具体类型
		// 泛型方法: 是在调用方法的时候指明泛型的具体类型

		/**
		 * 泛型方法
		 *
		 * @param tClass 传入的泛型实参
		 * @param <T> 返回值为T类型
		 * @return
		 * @throws IllegalAccessException
		 * @throws InstantiationException
		 * @description:
		 * 1) public 与返回值中间的<T>非常重要, 可以理解为声明此方法为泛型方法
		 * 2) 只有声明了<T>的方法才是泛型方法, 泛型类中的使用了泛型的成员方法并不是泛型方法
		 * 3) <T>表明该方法将使用泛型类型T, 此时才可以在方法中使用泛型类型T
		 * 4) 与泛型类的定义一样, 此处T可以随便写为任意标识: T,E,K,V
		 */
		public <T> T func5(Class<T> tClass) throws IllegalAccessException, InstantiationException {
			T instance = tClass.newInstance();
			return instance;
		}

		public void func6() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			Object obj = func5(Class.forName("generic.Generic"));
			logger.info("{}", obj);
		}

	}

	@Test
	public void func2() {
		// 泛型的类型只能是类类型(包括自定义类), 不能是基本类型
		// 传入的实参类型与泛型的类型参数类型相同, 即为Integer, String
		Generic1<Integer> genericInteger = new Generic1<>(123456);
		Generic1<String> genericString = new Generic1<>("key_value");

		logger.info("泛型测试, key is {}", genericInteger.getKey());
		logger.info("泛型测试, key is {}", genericString.getKey());
	}

}

// 泛型接口
interface Generator<T> {
	public T next();
}

/**
 * 未传入泛型实参时, 与泛型类的定义相同, 在声明类的时候, 需要将泛型的声明一并加入到类中
 *
 * @param <T>
 */
class FruitGenerator<T> implements Generator<T> {

	private T[] fruits;

	@Override
	public T next() {
		return null;
	}
}


