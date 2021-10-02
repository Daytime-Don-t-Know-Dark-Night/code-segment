package thinking.chapter14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ShowMethods {

	private static String usage = "usage";
	private static Pattern p = Pattern.compile("\\w+\\.");
	private static final Logger logger = LoggerFactory.getLogger(ShowMethods.class);

	public static void main(String[] args) {
		if (args.length < 1) {
			logger.info(usage);
			System.exit(0);
		}
		int lines = 0;

		try {
			Class<?> c = Class.forName(args[0]);
			Method[] methods = c.getMethods();
			Constructor<?>[] ctors = c.getConstructors();
			if (args.length == 1) {
				for (Method method : methods) {
					logger.info(p.matcher(method.toString()).replaceAll(""));
				}
				for (Constructor<?> ctor : ctors) {
					logger.info(p.matcher(ctor.toString()).replaceAll(""));
				}
				lines = methods.length + ctors.length;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
