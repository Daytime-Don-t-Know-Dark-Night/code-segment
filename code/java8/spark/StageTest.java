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

import static org.apache.spark.sql.functions.*;

public class StageTest {

	// 在Job中, 一次shuffle产生一个Stage
	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType schema = new StructType()
				.add("id", "int")
				.add("name", "string");

		List<Row> list1 = ImmutableList.of(
				RowFactory.create(1, "dingc"),
				RowFactory.create(2, "qidai"),
				RowFactory.create(3, "test")
		);

		List<Row> list2 = ImmutableList.of(
				RowFactory.create(1, "boluo"),
				RowFactory.create(2, "chuixue"),
				RowFactory.create(2, "chuixue")
		);

		Dataset<Row> ds1 = spark.createDataFrame(list1, schema);
		Dataset<Row> ds2 = spark.createDataFrame(list2, schema);

		// ds1: map -> filter
		ds1 = ds1.withColumn("name", concat(expr("name"), expr("'_'")))
				.where("id < 3");

		// ds2: group -> map
		// ds2会产生两个stage, 因为group会触发一次shuffle
		ds2 = ds2.groupBy("id")
				.agg(
						max("name")
				);

		ds1.as("a").join(ds2.as("b"),
				expr("a.id = b.id"),
				"outer"
		).repartition(13)
				.count();

		Uninterruptibles.sleepUninterruptibly(5000, TimeUnit.SECONDS);
	}

}
