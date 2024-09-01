package java8.spark;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.spark.sql.functions.expr;

public class CacheTest {

	public static void main(String[] args) {

		SparkSession spark = SparkSession
				.builder()
				.master("local")
				.config("spark.sql.crossJoin.enabled", "true")
				.getOrCreate();

		StructType schema = new StructType()
				.add("name", "string")
				.add("age", "int");

		List<Row> list = ImmutableList.of(
				RowFactory.create("dingc", 20),
				RowFactory.create("qidai", 30),
				RowFactory.create("qidai", 90)
		);

		Dataset<Row> ds = spark.createDataFrame(list, schema)
				.withColumn("age", expr("age * 1"))
				.where("age < 50")
				.persist();

		Dataset<Row> ds1 = ds.withColumn("age", expr("age + 10"));
		Dataset<Row> ds2 = ds.withColumn("age", expr("age + 20"));

		Dataset<Row> ds3 = ds1.as("a").join(ds2.as("b"),
				expr("a.age = b.age"),
				"outer"
		);
		ds3.count();

		Uninterruptibles.sleepUninterruptibly(5000, TimeUnit.SECONDS);
	}

}
