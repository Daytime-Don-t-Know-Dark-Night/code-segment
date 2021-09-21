package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL14 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[8]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("course", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "a"),
            RowFactory.create(1: java.lang.Integer, "b"),
            RowFactory.create(1: java.lang.Integer, "c"),
            RowFactory.create(1: java.lang.Integer, "e"),

            RowFactory.create(2: java.lang.Integer, "a"),
            RowFactory.create(2: java.lang.Integer, "c"),
            RowFactory.create(2: java.lang.Integer, "d"),
            RowFactory.create(2: java.lang.Integer, "f"),

            RowFactory.create(3: java.lang.Integer, "a"),
            RowFactory.create(3: java.lang.Integer, "b"),
            RowFactory.create(3: java.lang.Integer, "c"),
            RowFactory.create(3: java.lang.Integer, "e")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+------+
        // |id |course|
        // +---+------+
        // |1  |a     |
        // |1  |b     |
        // |1  |c     |
        // |1  |e     |
        // |2  |a     |
        // |2  |c     |
        // |2  |d     |
        // |2  |f     |
        // |3  |a     |
        // |3  |b     |
        // |3  |c     |
        // |3  |e     |
        // +---+------+

        ds.groupBy("id")
            .agg(
                expr("sum(case when course = 'a' then 1 else 0 end)").as("a"),
                expr("sum(case when course = 'b' then 1 else 0 end)").as("b"),
                expr("sum(case when course = 'c' then 1 else 0 end)").as("c"),
                expr("sum(case when course = 'd' then 1 else 0 end)").as("d"),
                expr("sum(case when course = 'e' then 1 else 0 end)").as("e"),
                expr("sum(case when course = 'f' then 1 else 0 end)").as("f")
            )
            .orderBy("id")
            .show(false)

        // +---+---+---+---+---+---+---+
        // |id |a  |b  |c  |d  |e  |f  |
        // +---+---+---+---+---+---+---+
        // |1  |1  |1  |1  |0  |1  |0  |
        // |2  |1  |0  |1  |1  |0  |1  |
        // |3  |1  |1  |1  |0  |1  |0  |
        // +---+---+---+---+---+---+---+
    }
}
