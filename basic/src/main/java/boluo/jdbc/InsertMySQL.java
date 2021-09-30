package boluo.jdbc;

import boluo.utils.Jdbcs;
import boluo.utils.Outputs;
import com.google.common.collect.Lists;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.types.StructType;

import java.util.List;

public class InsertMySQL {

	public static void main(String[] args) throws Exception {

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();

		String uri = "jdbc:mysql://local.landeli.com/test_boluo?characterEncoding=UTF-8&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&user=root&password=Xlpro2019";
		// String uri = "jdbc:mysql://local.boluo.com/test_boluo?characterEncoding=UTF-8&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&user=root&password=123";
		String filterPath = "file:///D:/filter";

		StructType schema = new StructType()
				.add("name", "string");

		List<Row> list = Lists.newArrayList();
		list.add(RowFactory.create("1"));
		list.add(RowFactory.create("2"));
		list.add(RowFactory.create("3"));
		list.add(RowFactory.create("4"));
		list.add(RowFactory.create("5"));

		// Dataset<Row> ds = spark.read().format("delta").load(filterPath);
		Dataset<Row> ds = spark.createDataFrame(list, schema);

		long startTime = System.currentTimeMillis();
		String name = "测试一百万条数据";
		JdbcOptionsInWrite opt = Jdbcs.options(uri, name, "name varchar(64)");
		// Outputs.createTable(ds, opt, "KEY(name)");
		Outputs.replace(ds, opt, "1=1");
		long endTime = System.currentTimeMillis();

		System.out.println("耗时: " + (endTime - startTime));
	}

}
