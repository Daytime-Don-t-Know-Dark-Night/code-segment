package com.boluo.spark.core;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @author chao
 * @date 2023/1/8 22:14
 * @desc
 */
public class ConnectHDFS {

    // Spark连接HDFS, avro文件
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();

        // avro文件在HDFS上的路径
        String path = "/path/123.avro";
        Dataset<Row> orc = spark.read()
                .format("avro")
                .option("basePath", "/datalake/.../ORD")
                .load(path);

    }

}
