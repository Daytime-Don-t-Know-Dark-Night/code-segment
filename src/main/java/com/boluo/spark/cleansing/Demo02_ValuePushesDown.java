package com.boluo.spark.cleansing;

import com.boluo.utils.SparkUtils;
import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author chao
 * @datetime 2025-03-23 22:10
 * @description
 */
public class Demo02_ValuePushesDown {

    private static final SparkSession spark = SparkUtils.initialSpark();
    private static final Dataset<Row> ds;

    static {
        StructType schema = new StructType()
                .add("offset", "int")
                .add("partition", "int")
                .add("raw_msg", "string")
                .add("created_timestamp", "timestamp");

        List<Row> list = ImmutableList.of(
                RowFactory.create(1, 0, "start,2025-03-21,AU", new Timestamp(System.currentTimeMillis())),
                RowFactory.create(2, 0, "{}", new Timestamp(System.currentTimeMillis())),
                RowFactory.create(3, 0, "completed", new Timestamp(System.currentTimeMillis()))
        );

        ds = spark.createDataFrame(list, schema);
    }


    // todo 把第一行的 2025-03-01, 下推到这一组数据中, 一直到 completed 为止
    public static void main(String[] args) {
        ds.show(false);

    }

}
