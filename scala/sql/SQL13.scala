package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL13 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[8]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("contents", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "i|have|a|good|time"),
            RowFactory.create(2: java.lang.Integer, "u|have|a|good|time")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+------------------+
        // |uid|contents          |
        // +---+------------------+
        // |1  |i|have|a|good|time|
        // |2  |u|have|a|good|time|
        // +---+------------------+
        // 统计每个单词出现的次数, 出现次数一样, 按照content名称排序

        ds.withColumn("content", explode(split(col("contents"), "\\|")))
            .groupBy("content")
            .agg(count("content").as("cnt"))
            .orderBy(desc("cnt"))
            .orderBy("content")
            .show(false)

        // +-------+---+
        // |content|cnt|
        // +-------+---+
        // |a      |2  |
        // |good   |2  |
        // |have   |2  |
        // |i      |1  |
        // |time   |2  |
        // |u      |1  |
        // +-------+---+
    }
}
