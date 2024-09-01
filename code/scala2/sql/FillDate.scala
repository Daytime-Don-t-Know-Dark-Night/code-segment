package scala2.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.api.java.UDF2
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.StructType

import scala.collection.{JavaConversions, mutable}
import org.apache.spark.sql.functions._

import java.sql.Date

object FillDate {

    def main(args: Array[String]): Unit = {
        // 填充日期
        val spark: SparkSession = SparkSession.builder.master("local[*]").getOrCreate
        val schema = new StructType()
            .add("date", "date")

        val rows = Array(
            RowFactory.create(Date.valueOf("2022-01-01")),
            RowFactory.create(Date.valueOf("2022-01-05")),
            RowFactory.create(Date.valueOf("2022-01-06")),
            RowFactory.create(Date.valueOf("2021-01-08")),
            RowFactory.create(Date.valueOf("2021-01-11"))
        )
        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
            .withColumn("rev", monotonically_increasing_id())
        ds.show(false)
        // +----------+---+
        // |date      |rev|
        // +----------+---+
        // |2022-04-01|0  |
        // |2022-04-05|1  |
        // |2022-04-06|2  |
        // |2021-04-08|3  |
        // |2021-04-11|4  |
        // +----------+---+

        // 增加行数, 将其中空缺的日期填满, 最后一行填充到今天
        // 1.lead()取下一行, udf构建数组, explode()将日期炸裂开
        // 2.构建一段日期,



    }

}
