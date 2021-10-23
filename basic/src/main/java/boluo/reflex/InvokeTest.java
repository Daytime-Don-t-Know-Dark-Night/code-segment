package boluo.reflex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InvokeTest {

	private static final Method[] methodSet;

	static {
		List<? extends Class<?>> classes;
		try {
			classes = ClassPath.from(Thread.currentThread().getContextClassLoader())
					.getTopLevelClasses("boluo.reflex")
					.stream()
					.map(ClassPath.ClassInfo::load)
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		methodSet = classes.stream()
				.flatMap(i -> Arrays.stream(i.getDeclaredMethods()))
				.filter(i -> Modifier.isPublic(i.getModifiers()))
				.sorted(Comparator.comparing(Method::toGenericString, String::compareTo))
				.toArray(Method[]::new);
	}

	public static void main(String[] args) {
		for (Method method : methodSet) {
			System.out.println(method);
		}
	}

	@Test
	public void func1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		// https://blog.csdn.net/weixin_40001125/article/details/90746726
		// https://zhuanlan.zhihu.com/p/350058223

		// getDeclaredMethod: 根据方法名和参数类型(可不填写)获得一个方法对象, 包括private修饰的
		Method m1 = Class.forName("boluo.reflex.Target1").getDeclaredMethod("func11");
		Assert.assertEquals("func11", m1.getName());

		//
		Target1 t1 = new Target1();
		// TODO
		Object obj = m1.invoke(t1, "dingc", "qidai");
		String res = String.valueOf(obj);
		Assert.assertEquals(res, "dingc");
	}

	@Test
	public void func2() {
		// getDeclaredMethods: 获取当前类汇总所有的方法, 包含私有的, 不包含父类中的
		Method[] declareMethods = Target1.class.getDeclaredMethods();
		Set<String> publicMethod = Arrays.stream(declareMethods)
				.filter(i -> Modifier.isPublic(i.getModifiers()))
				.map(Method::getName)
				.collect(Collectors.toSet());
		Assert.assertEquals(ImmutableSet.of("func11", "func12"), publicMethod);
	}


}
