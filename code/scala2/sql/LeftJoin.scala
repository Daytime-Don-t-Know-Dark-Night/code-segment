package scala2.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

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
            RowFactory.create(1: java.lang.Integer, "11", "egg"),
            RowFactory.create(2: java.lang.Integer, "12", "water"),
            RowFactory.create(3: java.lang.Integer, "12", "water"),
            RowFactory.create(4: java.lang.Integer, "13", "rice"),
            RowFactory.create(5: java.lang.Integer, "14", "apple")
        )

        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema1)
        val ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema2)
        ds1.show(false)
        ds2.show(false)

        // +---+------+
        // |id |dep_no|
        // +---+------+
        // |1  |11    |
        // |2  |12    |
        // |3  |13    |
        // |5  |14    |
        // +---+------+

        // +---+------+--------+
        // |id |dep_no|dep_name|
        // +---+------+--------+
        // |1  |11    |egg     |
        // |2  |12    |water   |
        // |3  |12    |water   |
        // |4  |13    |rice    |
        // |5  |14    |apple   |
        // +---+------+--------+

        ds1.as("a").join(
            ds2.as("b"),
            expr("a.dep_no = b.dep_no"),
            "left"
        ).show(false)

        // 由于表2中id为2和3的数据是重复的, 此时使用左连接便会得到比左表多的数据, 因为出现了1对n的数据
        // +---+------+---+------+--------+
        // |id |dep_no|id |dep_no|dep_name|
        // +---+------+---+------+--------+
        // |1  |11    |1  |11    |egg     |
        // |2  |12    |3  |12    |water   |
        // |2  |12    |2  |12    |water   |
        // |3  |13    |4  |13    |rice    |
        // |5  |14    |5  |14    |apple   |
        // +---+------+---+------+--------+

    }
}
