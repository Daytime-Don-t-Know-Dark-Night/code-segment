package scala2.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object RangeBetween {

    def main(args: Array[String]): Unit = {

        // row between 和 range between 控制开窗函数中窗户的大小
        val spark = SparkSession.builder().master("local[16]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("name", "string")
            .add("dates", "string")
            .add("income", "double")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-20", 1000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-21", 1000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-22", 1000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-24", 1000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-25", 1000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-28", 1000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", "2021-09-29", 1000: java.lang.Double)
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds = ds.withColumn("date", to_date(col("dates"), "yyyy-MM-dd")).drop("dates")
        ds.show(false)

        // +---+-----+----------+------+
        // |uid|name |date      |income|
        // +---+-----+----------+------+
        // |1  |dingc|2021-09-20|1000.0|
        // |1  |dingc|2021-09-21|1000.0|
        // |1  |dingc|2021-09-22|1000.0|
        // |1  |dingc|2021-09-24|1000.0|
        // |1  |dingc|2021-09-25|1000.0|
        // |1  |dingc|2021-09-28|1000.0|
        // |1  |dingc|2021-09-29|1000.0|
        // +---+-----+----------+------+
        // 新增一列: 用户三天内的收入之和

        ds.withColumn("date", to_timestamp(col("date")))
            .withColumn("sumIncome", expr("sum(income) over(order by date range between unbounded preceding and current row)"))
            .withColumn("allIncome", expr("sum(income) over(order by date range between unbounded preceding and unbounded following)"))
            .withColumn("income3day", expr("sum(income) over(order by date range between interval '2' day preceding and current row)"))
            .show(false)
    }
}
