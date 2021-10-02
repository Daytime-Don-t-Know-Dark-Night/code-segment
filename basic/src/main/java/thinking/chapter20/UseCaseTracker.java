package thinking.chapter20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UseCaseTracker {

	private static final Logger logger = LoggerFactory.getLogger(UseCaseTracker.class);

	public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
		for (Method m : cl.getDeclaredMethods()) {
			UseCase uc = m.getAnnotation(UseCase.class);
			if (uc != null) {
				logger.info("find use case: {}, {}", uc.id(), uc.description());
				useCases.remove(new Integer(uc.id()));
			}
		}

		for (Integer i : useCases) {
			logger.warn("missing use case: {}", i);
		}
	}

	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<>();
		Collections.addAll(useCases, 47, 48, 49, 50);
		trackUseCases(useCases, PasswordUtils.class);
	}
}
