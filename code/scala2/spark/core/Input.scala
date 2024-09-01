package scala2.spark.core

import org.apache.spark.sql.SparkSession

/**
 * @author chao
 * @date 2023/1/23 16:40
 * @desc
 */
object Input {

    def main(args: Array[String]): Unit = {
        SparkSession.builder().master("local[*]").getOrCreate()
        func3()
    }

    // 1. 加载txt文件
    def func1(): Unit = {
        val spark = SparkSession.active
        val path = System.getProperty("user.dir") + "/doc/spark/input.txt"
        val ds = spark.read.textFile(path)
        ds.show(false)
    }

    // 2. 加载json文件
    def func2(): Unit = {
        val spark = SparkSession.active
        val path = System.getProperty("user.dir") + "/doc/spark/input.json"
        val ds = spark.read.json(path)
        ds.show(false)
    }

    // 3. 加载csv文件
    def func3(): Unit = {
        val spark = SparkSession.active
        val path = System.getProperty("user.dir") + "/doc/spark/input.csv"
        val ds = spark.read
            .option("inferSchema", "false")
            .option("header", "true")
            .option("charset", "UTF-8")
            .csv(path)
        ds.show(false)
    }

}
