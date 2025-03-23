package com.boluo.spark.core;

import com.boluo.utils.SparkUtils;
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

    private static final SparkSession spark = SparkUtils.initialSpark();
    private static final Dataset<Row> ds;

    static {
        StructType schema = new StructType()
                .add("name", "string")
                .add("age", "int");

        List<Row> list = ImmutableList.of(
                RowFactory.create("dingc", 20),
                RowFactory.create("qidai", 30),
                RowFactory.create("qidai", 90)
        );

        ds = spark.createDataFrame(list, schema)
                .withColumn("age", expr("age * 1"))
                .where("age < 50")
                .persist();
    }

    public static void main(String[] args) {

        Dataset<Row> ds1 = ds.withColumn("age", expr("age + 10"));
        Dataset<Row> ds2 = ds.withColumn("age", expr("age + 20"));

        Dataset<Row> ds3 = ds1.as("a").join(ds2.as("b"),
                expr("a.age = b.age"),
                "outer"
        );
        ds3.count();

        System.out.println("pending, please check http://127.0.0.1:4040");
        Uninterruptibles.sleepUninterruptibly(5000, TimeUnit.SECONDS);
    }

}
