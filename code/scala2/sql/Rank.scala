package scala2.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object Rank {

    // https://blog.csdn.net/jisuanjiguoba/article/details/82832099
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("name", "string")
            .add("score", "double")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "dingc1", 70.0: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc2", 66.0: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc3", 30.0: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc4", 88.0: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc5", 66.0: java.lang.Double),

            RowFactory.create(2: java.lang.Integer, "dingc6", 70.0: java.lang.Double),
            RowFactory.create(2: java.lang.Integer, "dingc7", 50.0: java.lang.Double),
            RowFactory.create(2: java.lang.Integer, "dingc8", 65.0: java.lang.Double)
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+------+-----+
        // |id |name  |score|
        // +---+------+-----+
        // |1  |dingc1|70.0 |
        // |1  |dingc2|66.0 |
        // |1  |dingc3|30.0 |
        // |1  |dingc4|88.0 |
        // |1  |dingc5|66.0 |
        // |2  |dingc6|70.0 |
        // |2  |dingc7|50.0 |
        // |2  |dingc8|65.0 |
        // +---+------+-----+

        ds.withColumn("row_num", expr("row_number() over(partition by id order by score)"))
            .withColumn("rank", expr("rank() over(partition by id order by score)"))
            .withColumn("dense_rank", expr("dense_rank() over(partition by id order by score)"))
            .show(false)
    }
}
