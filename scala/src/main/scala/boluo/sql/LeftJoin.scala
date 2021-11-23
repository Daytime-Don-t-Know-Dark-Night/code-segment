package boluo.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

object LeftJoin {

    // https://blog.csdn.net/JOKER0707/article/details/101569792
    // left join 查询结果数量>左表数量的情况
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema1 = new StructType()
            .add("id", "int")
            .add("dep_no", "string")

        val schema2 = new StructType()
            .add("id", "int")
            .add("dep_no", "string")
            .add("dep_name", "string")

        val rows1 = Array(
            RowFactory.create(1: java.lang.Integer, "11"),
            RowFactory.create(2: java.lang.Integer, "12"),
            RowFactory.create(3: java.lang.Integer, "13"),
            RowFactory.create(5: java.lang.Integer, "14")
        )

        val rows2 = Array(
            RowFactory.create(1: java.lang.Integer, "11"),
            RowFactory.create(2: java.lang.Integer, "12"),
            RowFactory.create(3: java.lang.Integer, "13"),
            RowFactory.create(5: java.lang.Integer, "14")
        )

    }
}
