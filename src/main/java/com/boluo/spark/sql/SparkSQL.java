package com.boluo.spark.sql;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.concurrent.TimeUnit;

import static org.apache.spark.sql.functions.*;

/**
 * @Author dingc
 * @Date 2022/5/9 20:50
 */
public class SparkSQL {

	public static void main(String[] args) {
		func1();
	}

	public static void func1() {
		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType scheme = new StructType()
				.add("id", "string")
				.add("name", "string")
				.add("age", "double");

		Row row1 = RowFactory.create("1", "stu1", 70.0);
		Row row2 = RowFactory.create("2", "stu1", 80.0);
		Row row3 = RowFactory.create("3", "stu2", 90.0);
		Row row4 = RowFactory.create("4", "stu2", 90.0);

		Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4), scheme);
		ds = ds.withColumn("avg", expr("mean(age) over (partition by name)"))
				.withColumn("pop", expr("var_pop(age) over (partition by name)"))
				.withColumn("sum", expr("sum(age) over (partition by name)"));
		ds.show(false);
		Uninterruptibles.sleepUninterruptibly(5000, TimeUnit.SECONDS);
	}

}
