package scala2.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL15 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("unix", "long")
            .add("date1", "string")
            .add("date2", "string")

        val rows = Array(
            RowFactory.create(1564545445: java.lang.Long, "2019-07-31 11:57:25", "2019-07-31 11:57")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +----------+-------------------+----------------+
        // |unix      |date1              |date2           |
        // +----------+-------------------+----------------+
        // |1564545445|2019-07-31 11:57:25|2019-07-31 11:57|
        // +----------+-------------------+----------------+


        ds.withColumn("unix_to_timestamp1", to_timestamp(from_unixtime(col("unix"))))
            .withColumn("unix_to_timestamp2", to_timestamp(from_unixtime(col("unix")), "yyyy-MM-dd HH:mm"))
            .withColumn("timestamp_to_unix1", unix_timestamp(col("date1")))
            .withColumn("timestamp_to_unix2", unix_timestamp(col("date2")))
            .show(false)
    }
}
