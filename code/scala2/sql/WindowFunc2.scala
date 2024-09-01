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
 * @Date 2022/3/7 20:15
 */
object WindowFunc2 {

    def main(args: Array[String]): Unit = {

        // 开窗函数的扩展窗口
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("name", "string")
            .add("subject", "string")
            .add("score", "int")
            .add("time", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "菠萝", "语文", 10: java.lang.Integer, "2020-09-08"),
            RowFactory.create(2: java.lang.Integer, "菠萝", "数学", 20: java.lang.Integer, "2020-09-10"),
            RowFactory.create(3: java.lang.Integer, "菠萝", "英语", 30: java.lang.Integer, "2020-09-13"),
            RowFactory.create(4: java.lang.Integer, "吹雪", "语文", 15: java.lang.Integer, "2020-09-07"),
            RowFactory.create(5: java.lang.Integer, "吹雪", "数学", 25: java.lang.Integer, "2020-09-08"),
            RowFactory.create(6: java.lang.Integer, "吹雪", "英语", 35: java.lang.Integer, "2020-09-15")
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        // ds.show(false)
        // +---+----+-------+-----+
        // |id |name|subject|score|
        // +---+----+-------+-----+
        // |1  |菠萝|语文   |10   |
        // |2  |菠萝|数学   |20   |
        // |3  |菠萝|英语   |30   |
        // |4  |吹雪|语文   |15   |
        // |5  |吹雪|数学   |25   |
        // |6  |吹雪|英语   |35   |
        // +---+----+-------+-----+

        ds.show(false)
        ds = ds.withColumn("time1", expr("lag(time,1,'2020-09-07') over (partition by name order by time)"))

        ds = ds.withColumn("time", to_date(col("time")))
            .withColumn("time1", to_date(col("time1")))

        ds.withColumn("res", datediff(col("time"), col("time1")))
            .show(false)

        ds.withColumn("rk", expr("row_number() over (order by score)"))
            .show(false)

        ds.withColumn("rk", expr("row_number() over ( partition by name order by score)"))
            .show(false)

        // 开窗求每人的总分
        ds.withColumn("sum", expr("sum(score) over ()")).show(false)
        ds.withColumn("sum", expr("sum(score) over (partition by name order by score)")).show(false)
        ds.withColumn("sum", expr("sum(score) over (partition by name)")).show(false)
        // +---+----+-------+-----+---+
        // |id |name|subject|score|sum|
        // +---+----+-------+-----+---+
        // |1  |菠萝|语文   |10   |60 |
        // |2  |菠萝|数学   |20   |60 |
        // |3  |菠萝|英语   |30   |60 |
        // |4  |吹雪|语文   |15   |75 |
        // |5  |吹雪|数学   |25   |75 |
        // |6  |吹雪|英语   |35   |75 |
        // +---+----+-------+-----+---+

        // sql中的函数分为两种: 排序函数和聚合函数
        // 排序函数: row_number(), rank()... over()中的 order by 只起到窗口内排序的作用
        // 聚合函数: max(), min(), count()... over()中的 order by 不仅起到窗口排序的作用, 还起到窗口内从当前行到之前所有行的聚合

        ds.withColumn("sum", expr("sum(score) over (partition by name order by score)")).show(false)
        ds.withColumn("sum", expr("sum(score) over (partition by name order by score range between unbounded preceding and current row)")).show(false)
        // +---+----+-------+-----+---+
        // |id |name|subject|score|sum|
        // +---+----+-------+-----+---+
        // |1  |菠萝|语文   |10   |10 |
        // |2  |菠萝|数学   |20   |30 |
        // |3  |菠萝|英语   |30   |60 |
        // |4  |吹雪|语文   |15   |15 |
        // |5  |吹雪|数学   |25   |40 |
        // |6  |吹雪|英语   |35   |75 |
        // +---+----+-------+-----+---+
    }

}
