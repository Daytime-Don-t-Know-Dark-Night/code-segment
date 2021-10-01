package thinking.chapter14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface HasBatteries {
}

interface Waterproof {
}

interface Shoots {
}

class Toy {

	Toy() {
	}

	Toy(int i) {
	}
}

class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots {
	FancyToy() {
		super(1);
	}
}

public class ToyTest {

	private static final Logger logger = LoggerFactory.getLogger(ToyTest.class);

	static void printInfo(Class c) {
		logger.info("Class name: {}, is interface? [{}]", c.getName(), c.isInterface());
		logger.info("Simple name: {}", c.getSimpleName());
		logger.info("Canonical name: {}", c.getCanonicalName());
	}

	public static void main(String[] args) {
		Class<?> c = null;
		try {
			c = Class.forName("thinking.chapter14.FancyToy");
		} catch (ClassNotFoundException e) {
			logger.info("Can't find FancyToy");
			System.exit(1);
		}
		printInfo(c);

		for (Class<?> face : c.getInterfaces()) {
			printInfo(face);
		}

		Class<?> up = c.getSuperclass();
		Object obj = null;
		try {
			obj = up.newInstance();
		} catch (InstantiationException e) {
			logger.error("Cannot instantiate");
			e.printStackTrace();
			System.exit(1);
		} catch (IllegalAccessException e) {
			logger.error("Cannot access");
			e.printStackTrace();
			System.exit(1);
		}
		printInfo(obj.getClass());
	}

}
