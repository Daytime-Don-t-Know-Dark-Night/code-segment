package scala2.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions.{expr, count, sum}
import scala.collection.JavaConversions

object SQL02 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("channel", "int")
            .add("min", "int")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, 23: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, 12: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, 1: java.lang.Integer, 12: java.lang.Integer),
            RowFactory.create(4: java.lang.Integer, 1: java.lang.Integer, 32: java.lang.Integer),
            RowFactory.create(5: java.lang.Integer, 1: java.lang.Integer, 342: java.lang.Integer),
            RowFactory.create(6: java.lang.Integer, 2: java.lang.Integer, 13: java.lang.Integer),
            RowFactory.create(7: java.lang.Integer, 2: java.lang.Integer, 34: java.lang.Integer),
            RowFactory.create(8: java.lang.Integer, 2: java.lang.Integer, 13: java.lang.Integer),
            RowFactory.create(9: java.lang.Integer, 2: java.lang.Integer, 134: java.lang.Integer)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        //  +---+-------+---+
        //  |uid|channel|min|
        //  +---+-------+---+
        //  |1  |1      |23 |
        //  |2  |1      |12 |
        //  |3  |1      |12 |
        //  |4  |1      |32 |
        //  |5  |1      |342|
        //  |6  |2      |13 |
        //  |7  |2      |34 |
        //  |8  |2      |13 |
        //  |9  |2      |134|
        //  +---+-------+---+
        // 求每个栏目的被观看次数及累计观看时长

        ds.groupBy("channel")
            .agg(count("channel").as("count"), sum("min").as("sum"))
            .show(false)

    }
}
