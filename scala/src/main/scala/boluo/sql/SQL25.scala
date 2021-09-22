package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL25 {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[16]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("dates", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "2019-08-01"),
            RowFactory.create(1: java.lang.Integer, "2019-08-02"),
            RowFactory.create(1: java.lang.Integer, "2019-08-03"),
            RowFactory.create(2: java.lang.Integer, "2019-08-01"),
            RowFactory.create(2: java.lang.Integer, "2019-08-02"),
            RowFactory.create(3: java.lang.Integer, "2019-08-01"),
            RowFactory.create(3: java.lang.Integer, "2019-08-03"),
            RowFactory.create(4: java.lang.Integer, "2019-07-28"),
            RowFactory.create(4: java.lang.Integer, "2019-07-29"),
            RowFactory.create(4: java.lang.Integer, "2019-08-01"),
            RowFactory.create(4: java.lang.Integer, "2019-08-02"),
            RowFactory.create(4: java.lang.Integer, "2019-08-03")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)
        ds.withColumn("date", date_format(col("dates"), "yyyy-MM-dd"))
            .drop("dates")
            .show(false)

        // +---+----------+
        // |uid|date      |
        // +---+----------+
        // |1  |2019-08-01|
        // |1  |2019-08-02|
        // |1  |2019-08-03|
        // |2  |2019-08-01|
        // |2  |2019-08-02|
        // |3  |2019-08-01|
        // |3  |2019-08-03|
        // |4  |2019-07-28|
        // |4  |2019-07-29|
        // |4  |2019-08-01|
        // |4  |2019-08-02|
        // |4  |2019-08-03|
        // +---+----------+
        // 求每个用户连续登录的最大天数

        // 一般解法
        // 1. 先按时间排序,
        // 2. 登录时间减去排序的序号, 得到一个日期
        // 3. 按照这个日期分组求和, 最大的一个数就是连续登录最长的天数


    }
}
