package com.boluo.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/**
 * @author chao
 * @datetime 2025-03-20 21:17
 * @description
 */
public class SparkUtils {

    public static SparkSession initialSpark() {
        String osName = System.getProperty("os.name");
        System.out.println("System name: " + osName);

        return SparkSession.builder()
                .appName("SparkAppName")
                .master("local[1]")
                .config(loadSparkConfig())
                .getOrCreate();
    }

    private static SparkConf loadSparkConfig() {
        SparkConf conf = new SparkConf();
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        return conf;
    }

}
