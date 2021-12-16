package boluo.generic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Generic {

	private static final Logger logger = LoggerFactory.getLogger(Generic.class);

	// TODO https://juejin.cn/post/6960913691990556680
	public static void main(String[] args) {

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


}
