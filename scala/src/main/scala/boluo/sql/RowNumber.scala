package boluo.sql

import org.apache.spark.sql.functions.expr
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType

import scala.collection.JavaConversions

object RowNumber {

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
            RowFactory.create(2: java.lang.Integer, 3: java.lang.Integer, "type1")
        )

        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds1.show(false)
        ds1.withColumn("row_num", expr(""))

    }
}
