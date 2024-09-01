package scala2.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL10 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[8]").getOrCreate()
        val schema1 = new StructType()
            .add("tag", "string")

        val rows1 = Array(
            RowFactory.create("1,2,3"),
            RowFactory.create("1,2"),
            RowFactory.create("2,3")
        )

        var ds1: DataFrame = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema1)
        ds1 = ds1.withColumn("tags", expr("split(tag, ',')")).drop("tag")
        ds1.show(false)

        // +---------+
        // |tags     |
        // +---------+
        // |[1, 2, 3]|
        // |[1, 2]   |
        // |[2, 3]   |
        // +---------+

        val schema2 = new StructType()
            .add("id", "int")
            .add("lab", "string")

        val rows2 = Array(
            RowFactory.create(1: java.lang.Integer, "A"),
            RowFactory.create(2: java.lang.Integer, "B"),
            RowFactory.create(3: java.lang.Integer, "C")
        )

        val ds2: DataFrame = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema2)
        ds2.show(false)

        // +---+---+
        // |id |lab|
        // +---+---+
        // |1  |A  |
        // |2  |B  |
        // |3  |C  |
        // +---+---+


        // 结果如下:
        // +-----+-----+
        // |ids  |tags_|
        // +-----+-----+
        // |2,3  |B,C  |
        // |1,2  |A,B  |
        // |1,2,3|A,B,C|
        // +-----+-----+

        ds1.withColumn("tag", explode(col("tags")))
            .as("a")
            .join(
                ds2.as("b"),
                expr("a.tag = b.id"),
                "left"
            )
            .groupBy("tags")
            .agg(
                concat_ws(",", collect_list(col("b.id"))).as("ids"),
                concat_ws(",", collect_list(col("b.lab"))).as("tags_")
            )
            .drop("tags")
            .show(false)
    }
}
