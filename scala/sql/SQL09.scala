package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.{ArrayType, DataType, StructType}
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL09 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("tag", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "1,2,3"),
            RowFactory.create(2: java.lang.Integer, "2,3"),
            RowFactory.create(3: java.lang.Integer, "1,2")
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds = ds.withColumn("tags", expr("split(tag, ',')")).drop("tag")
        ds.show(false)

        // +---+---------+
        // |uid|tags     |
        // +---+---------+
        // |1  |[1, 2, 3]|
        // |2  |[2, 3]   |
        // |3  |[1, 2]   |
        // +---+---------+
        // 列转行

        ds = ds.withColumn("tag", explode(col("tags"))).drop("tags")
        ds.show(false)

        // +---+---+
        // |uid|tag|
        // +---+---+
        // |1  |1  |
        // |1  |2  |
        // |1  |3  |
        // |2  |2  |
        // |2  |3  |
        // |3  |1  |
        // |3  |2  |
        // +---+---+
    }

}
