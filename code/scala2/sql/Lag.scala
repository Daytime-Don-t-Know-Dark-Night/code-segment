package scala2.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object Lag {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("rev", "int")
            .add("type", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, "type1"),
            RowFactory.create(1: java.lang.Integer, 2: java.lang.Integer, "type1"),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, "type1"),
            RowFactory.create(2: java.lang.Integer, 2: java.lang.Integer, "type2"),
            RowFactory.create(2: java.lang.Integer, 3: java.lang.Integer, "type1"),
            RowFactory.create(2: java.lang.Integer, 4: java.lang.Integer, "type1")
        )

        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds1.show(false)

        // +---+---+-----+
        // |id |rev|type |
        // +---+---+-----+
        // |1  |1  |type1|
        // |1  |2  |type1|

        // |2  |1  |type1|
        // |2  |2  |type2|
        // |2  |3  |type1|
        // |2  |4  |type1|
        // +---+---+-----+

        ds1.groupBy("id").agg(
            sum("rev")
        )

        ds1.withColumn("sum", expr("sum(rev) over(partition by id order by rev)"))
            .show(false)

        ds1.withColumn("type_", expr("lag(type,1,0) over(partition by id order by rev)"))
            .withColumn("is_delete", expr("case when type = type_ then 0 else 1 end"))
            .show(false)

        ds1.withColumn("type_", lead(col("type"), 1, "0")
            .over(Window.partitionBy("id").orderBy("rev")))
            .show(false)

        ds1.withColumn("first_value", expr("first_value(type) over(partition by id order by rev)"))
            .show(false)

        // 默认窗口是从第一行到 current row
        ds1.withColumn("last_value", expr("last_value(type) over(partition by id order by rev)"))
            .show(false)

        // 把窗口改为 从 current row 到 unbounded following
        ds1.withColumn("last_value", expr("last_value(type) over(partition by id order by rev rows between current row and unbounded following)"))
            .show(false)
    }
}
