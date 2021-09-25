package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL16 {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("date", "string")

        val rows = Array(
            RowFactory.create("20190730"),
            RowFactory.create("20190731")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +--------+
        // |date    |
        // +--------+
        // |20190730|
        // |20190731|
        // +--------+

        ds.withColumn("dates", to_date(col("date"), "yyyyMMdd"))
            .show(false)
    }
}
