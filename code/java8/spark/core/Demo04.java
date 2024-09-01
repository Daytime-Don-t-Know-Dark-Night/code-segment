package java8.spark.core;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2024-06-23 20:11
 * @description
 */
public class Demo04 {

    private static Dataset<Row> ds;

    static {
        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("id", "int")
                .add("name", "string")
                .add("age", "int")
                .add("email", "string")
                .add("phone", "string")
                .add("image", "string");

        Row row1 = RowFactory.create(1, "dingc", 20, "cding@state.com", "16631086282", "img/dingc");
        Row row2 = RowFactory.create(2, "boluo", 20, "boluo@state.com", "17731086282", "img/boluo");
        Row row3 = RowFactory.create(3, "qidai", 30, "qidai@state.com", "18831086282", "img/qidai");
        ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3), scheme);
    }


    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println(now.format(formatter));


        SparkSession.active().sparkContext().setJobDescription("计算age");
        ds.groupBy("age").agg(count("name")).show(false);
        Uninterruptibles.sleepUninterruptibly(Duration.ofDays(1));
    }


}
