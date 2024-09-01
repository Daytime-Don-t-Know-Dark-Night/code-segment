package scala2.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL11 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[8]").getOrCreate()
        val schema = new StructType()
            .add("id", "string")
            .add("tag", "string")
            .add("flag", "int")

        val rows = Array(
            RowFactory.create("a", "b", 2: java.lang.Integer),
            RowFactory.create("a", "b", 1: java.lang.Integer),
            RowFactory.create("a", "b", 3: java.lang.Integer),
            RowFactory.create("c", "d", 6: java.lang.Integer),
            RowFactory.create("c", "d", 8: java.lang.Integer),
            RowFactory.create("c", "d", 8: java.lang.Integer)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+---+----+
        // |id |tag|flag|
        // +---+---+----+
        // |a  |b  |2   |
        // |a  |b  |1   |
        // |a  |b  |3   |
        // |c  |d  |6   |
        // |c  |d  |8   |
        // |c  |d  |8   |
        // +---+---+----+

        // 结果如下:
        // +---+---+-------------------------------+
        // |id |tag|concat_ws(|, collect_set(flag))|
        // +---+---+-------------------------------+
        // |c  |d  |6|8                            |
        // |a  |b  |1|2|3                          |
        // +---+---+-------------------------------+

        ds.groupBy("id", "tag")
            .agg(
                concat_ws("|", collect_set(col("flag")))
            ).show(false)
    }
}
