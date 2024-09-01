package scala2.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.api.java.UDF2
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.StructType

import scala.collection.{JavaConversions, mutable}
import org.apache.spark.sql.functions._

object DailyUserIncrease {

    def main(args: Array[String]): Unit = {

        val spark: SparkSession = SparkSession.builder.master("local[*]").getOrCreate
        val schema = new StructType()
            .add("date", "string")
            .add("userId", "string")
            .add("orderNo", "string")

        val rows = Array(
            RowFactory.create("2021-12-01", "张三", "001"),
            RowFactory.create("2021-12-02", "李四", "002"),
            RowFactory.create("2021-12-03", "张三", "003"),
            RowFactory.create("2021-12-03", "王五", "004"),
            RowFactory.create("2021-12-04", "张三", "005"),
            RowFactory.create("2021-12-04", "赵四", "006"),
            RowFactory.create("2021-12-05", "张三", "007")
        )
        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        // ds.show(false)
        // +----------+------+-------+
        // |date      |userId|orderNo|
        // +----------+------+-------+
        // |2021-12-01|张三  |001    |
        // |2021-12-02|李四  |002    |
        // |2021-12-03|张三  |003    |
        // |2021-12-03|王五  |004    |
        // |2021-12-04|张三  |005    |
        // |2021-12-04|赵四  |006    |
        // |2021-12-05|张三  |007    |
        // +----------+------+-------+

        // 统计每日的用户增加量
        // 用户的首次订单出现时间, 也就是用户增加的时间

        ds = ds.withColumn("rk", expr("row_number() over(partition by userId order by date)"))
            .withColumn("是否用户首单", expr("if(rk=1,'yes',null)"))

        ds.groupBy("date")
            .agg(
                count("是否用户首单")
            )
            .orderBy("date")
            .show(false)

    }

}
