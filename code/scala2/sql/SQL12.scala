package scala2.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL12 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[8]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("name", "string")
            .add("tags", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "goudan", "chihuo,huaci"),
            RowFactory.create(2: java.lang.Integer, "mazi", "sleep"),
            RowFactory.create(3: java.lang.Integer, "laotie", "paly")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+------+------------+
        // |uid|name  |tags        |
        // +---+------+------------+
        // |1  |goudan|chihuo,huaci|
        // |2  |mazi  |sleep       |
        // |3  |laotie|paly        |
        // +---+------+------------+

        ds.withColumn("tag", explode(split(col("tags"), ",")))
            .drop("tags")
            .show(false)

        // +---+------+------+
        // |uid|name  |tag   |
        // +---+------+------+
        // |1  |goudan|chihuo|
        // |1  |goudan|huaci |
        // |2  |mazi  |sleep |
        // |3  |laotie|paly  |
        // +---+------+------+
    }
}
