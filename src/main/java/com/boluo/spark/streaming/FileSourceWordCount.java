package com.boluo.spark.streaming;

import org.apache.spark.sql.SparkSession;

/**
 * @author chao
 * @datetime 2024-09-02 0:24
 * @description
 */
public class FileSourceWordCount {

    // https://zhuanlan.zhihu.com/p/359448701

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .config("spark.default.parallelism", 2)
                .config("spark.sql.streaming.checkpointLocation", "")
                .appName("StructuredNetworkWordCount")
                .getOrCreate();

        spark.sparkContext().setLogLevel("WARN");

        String dataPath = "C:/Users/dingc/IdeaProjects/code-segment/doc";



    }

}
