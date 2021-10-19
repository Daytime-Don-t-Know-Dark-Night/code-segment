package boluo.reflex;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
}
