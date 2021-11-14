package boluo;

import boluo.utils.Jdbcs;
import boluo.utils.YouData;
import com.google.common.collect.Lists;
import org.apache.spark.SparkException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.types.StructType;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.apache.spark.sql.functions.*;

// 往本地数据库插入100条数据测试
public class ImportTest {

	public static void main(String[] args) throws SQLException, SparkException, ExecutionException, InvocationTargetException {

		String test_uri = "jdbc:mysql://localhost/test_boluo?characterEncoding=UTF-8&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&user=root&password=root";

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType schema = new StructType()
				.add("id", "string")
				.add("name", "string")
				.add("amount", "string")
				.add("remark", "string");

		List<Row> list = Lists.newArrayList();
		Stream.iterate(0, i -> i + 1).limit(100).forEach(i -> {
			int random = (int) (Math.random() * 10);
			Row row = RowFactory.create("id" + i, "name" + random, "0.2", "remark");
			list.add(row);
		});

		Dataset<Row> ds = spark.createDataFrame(list, schema);
		// ds.show(false);
		String name = "测试一百万条数据";
		JdbcOptionsInWrite opt = Jdbcs.options(test_uri, name, "`date` varchar(64)");
		YouData.replace(ds, "", opt, expr("int(rand()*5)"));
	}

}
