package scala2.sql

import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Uninterruptibles
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

import java.util.concurrent.TimeUnit
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

/**
 * @Author dingc
 * @Date 2022-09-13 23:07
 * @Description
 */
object Demo {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("orderNo", "string")
            .add("userId", "string")
            .add("time", "string")

        val rows = Array(
            RowFactory.create("00A", "001", "2020-04-15"),
            RowFactory.create("00B", "002", "2020-05-01"),
            RowFactory.create("00C", "002", "2020-05-07"),
            RowFactory.create("00D", "002", "2020-05-09"),
            RowFactory.create("00E", "002", "2020-05-15"),
            RowFactory.create("00F", "002", "2020-05-26"),
            RowFactory.create("00G", "002", "2020-05-20"),
            RowFactory.create("00G", "003", "2020-05-31"),
            RowFactory.create("00G", "004", "2020-05-25"),
            RowFactory.create("00H", "008", "2020-06-07")
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        ds = ds.where(String.format("time >= '2020-05-01' and time < '2020-06-01'"))
        ds = ds.withColumn("count", expr("count(orderNo) over (partition by userId)"))
        ds = ds.selectExpr("userId", "count").dropDuplicates()
        ds.show(false)

        // 1. case when
        var cond = "case when count >= 0 and count < 6 then '[0-5]' " +
            "when count >= 6 and count < 11 then '[6-10]' end"
        ds = ds.withColumn("tmp", expr(cond))
        ds.groupBy("tmp")
            .agg(
                count("userId")
            )
            .show(false)

    }

}
