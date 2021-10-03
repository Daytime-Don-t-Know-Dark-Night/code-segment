package thinking.chapter20.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author dingc
 * @Date 2021/10/2 21:19
 * @Description This is a table creator, It will read a class file, check its database annotations, and generate statements to create the database
 * @Since version-1.0
 */
public class TableCreator {

	private static final Logger logger = LoggerFactory.getLogger(TableCreator.class);

	// TODO page-627
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			logger.info("arguments: annotated classes");
			System.exit(0);
		}

		for (String className : args) {
			Class<?> cl = Class.forName(className);
			DBTable deTable = cl.getAnnotation(DBTable.class);
		}

	}
}
