package com.boluo.spark.cleansing;

import com.boluo.utils.SparkUtils;
import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;


/**
 * @author chao
 * @datetime 2025-03-25 23:19
 * @description
 */
public class Demo02_ValuePushDown {

    private static final SparkSession spark = SparkUtils.initialSpark();
    private static final Dataset<Row> bronzeDs;
    private static final Dataset<Row> eventDs;

    static {
        bronzeDs = spark.createDataFrame(
                ImmutableList.of(
                        RowFactory.create("1", "0", "start,AU,2025-03-24", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("2", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("3", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("4", "0", "completed,2 .", new Timestamp(System.currentTimeMillis())),

                        RowFactory.create("5", "0", "start,JP,2025-03-24", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("6", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("7", "0", "completed,1.", new Timestamp(System.currentTimeMillis())),

                        RowFactory.create("8", "0", "start,AU,2025-03-25", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("9", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("10", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("11", "0", "completed,3.", new Timestamp(System.currentTimeMillis())),

                        RowFactory.create("12", "0", "start,AU,2025-03-26", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("13", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("14", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("15", "0", "completed,2.", new Timestamp(System.currentTimeMillis())),

                        RowFactory.create("16", "0", "start,JP,2025-03-26", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("17", "0", "A,B", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("18", "0", "completed,1.", new Timestamp(System.currentTimeMillis()))
                ), new StructType()
                        .add("kafka_offset", "string")
                        .add("kafka_partition", "string")
                        .add("kafka_raw_msg", "string")
                        .add("created_timestamp", "timestamp")
        );

        eventDs = spark.createDataFrame(
                ImmutableList.of(
                        RowFactory.create("1", "AU", "2025-03-23", "MATCH", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("2", "JP", "2025-03-23", "MATCH", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("3", "AU", "2025-03-24", "MATCH", new Timestamp(System.currentTimeMillis())),
                        RowFactory.create("4", "JP", "2025-03-24", "MATCH", new Timestamp(System.currentTimeMillis()))
                ), new StructType()
                        .add("recon_id", "string")
                        .add("branch", "string")
                        .add("eod_date", "string")
                        .add("status", "string")
                        .add("created_timestamp", "timestamp")
        );
    }


    public static void main(String[] args) {

        Dataset<Row> newBronzeDs = bronzeDs
                .withColumn("branch", expr("if(kafka_raw_msg like 'start%', split(kafka_raw_msg, ',')[1], null)"))
                .withColumn("eod_date", expr("if(kafka_raw_msg like 'start%', split(kafka_raw_msg, ',')[2], null)"))
                .withColumn("count", expr("if(kafka_raw_msg like 'completed%', split(kafka_raw_msg, ',')[1], null)"))
                .withColumn("rank", expr("sum(if(eod_date is not null, 1, 0)) over (order by cast(kafka_offset as bigint))"))
                .withColumn("branch", expr("first_value(branch) over (partition by rank order by cast(kafka_offset as bigint))"))
                .withColumn("eod_date", expr("first_value(eod_date) over (partition by rank order by cast(kafka_offset as bigint))"))
                .withColumn("count", expr("first_value(count) over (partition by rank order by cast(kafka_offset as bigint) desc)"));

        newBronzeDs.show(false);
        Dataset<Row> bronzeData = newBronzeDs.groupBy("branch", "eod_date").agg(max("created_timestamp").as("created_timestamp"));
        Dataset<Row> eventData = eventDs.groupBy("branch", "eod_date").agg(max("created_timestamp").as("created_timestamp"));

        bronzeData.show(false);
        // +------+----------+------+----------+
        // |branch|eod_date  |branch|eod_date  |
        // +------+----------+------+----------+
        // |AU    |2025-03-24|AU    |2025-03-24|
        // |JP    |2025-03-24|JP    |2025-03-24|
        // |AU    |2025-03-25|AU    |2025-03-25|
        // |AU    |2025-03-26|AU    |2025-03-26|
        // |JP    |2025-03-26|JP    |2025-03-26|
        // +------+----------+------+----------+

        eventData.show(false);
        // +------+----------+------+----------+
        // |branch|eod_date  |branch|eod_date  |
        // +------+----------+------+----------+
        // |AU    |2025-03-24|AU    |2025-03-24|
        // |JP    |2025-03-24|JP    |2025-03-24|
        // |AU    |2025-03-23|AU    |2025-03-23|
        // |JP    |2025-03-23|JP    |2025-03-23|
        // +------+----------+------+----------+

        List<Tuple2<String, String>> reconSite = bronzeData.select("branch", "eod_date").exceptAll(eventData.select("branch", "eod_date"))
                .collectAsList().stream().map(row -> Tuple2.apply(row.getString(0), row.getString(1)))
                .collect(Collectors.toList());
        System.out.println("need to be recon site: " + reconSite);

        for (Tuple2<String, String> tuple2 : reconSite) {

        }

    }

}
