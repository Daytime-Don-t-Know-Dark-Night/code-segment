package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object RowsBetween {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[16]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("name", "string")
            .add("date", "int")
            .add("income", "double")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "dingc", 1: java.lang.Integer, 2000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 2: java.lang.Integer, 1500: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 3: java.lang.Integer, 3000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 4: java.lang.Integer, 1800: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 5: java.lang.Integer, 2200: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 6: java.lang.Integer, 3100: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 7: java.lang.Integer, 4000: java.lang.Double),
            RowFactory.create(1: java.lang.Integer, "dingc", 8: java.lang.Integer, 1600: java.lang.Double)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+-----+----+------+
        // |uid|name |date|income|
        // +---+-----+----+------+
        // |1  |dingc|1   |2000.0|
        // |1  |dingc|2   |1500.0|
        // |1  |dingc|3   |3000.0|
        // |1  |dingc|4   |1800.0|
        // |1  |dingc|5   |2200.0|
        // |1  |dingc|6   |3100.0|
        // |1  |dingc|7   |4000.0|
        // |1  |dingc|8   |1600.0|
        // +---+-----+----+------+
        // 求每个用户三天内的总收入

        ds.withColumn("incomeAll", expr("sum(income) over(order by date rows between unbounded preceding and current row)"))
            .withColumn("income3days", expr("sum(income) over(order by date rows between 2 preceding and 0 following)"))
            .show(false)
    }
}
