package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL17 {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[16]").getOrCreate()
        val schema = new StructType()
            .add("店铺", "string")
            .add("月份", "int")
            .add("金额", "int")

        val rows = Array(
            RowFactory.create("a", 1: java.lang.Integer, 150: java.lang.Integer),
            RowFactory.create("a", 1: java.lang.Integer, 200: java.lang.Integer),
            RowFactory.create("b", 1: java.lang.Integer, 1000: java.lang.Integer),
            RowFactory.create("b", 1: java.lang.Integer, 800: java.lang.Integer),
            RowFactory.create("c", 1: java.lang.Integer, 800: java.lang.Integer),
            RowFactory.create("c", 1: java.lang.Integer, 250: java.lang.Integer),
            RowFactory.create("b", 1: java.lang.Integer, 250: java.lang.Integer),
            RowFactory.create("a", 2: java.lang.Integer, 2000: java.lang.Integer),
            RowFactory.create("a", 2: java.lang.Integer, 3000: java.lang.Integer),
            RowFactory.create("b", 2: java.lang.Integer, 1000: java.lang.Integer),
            RowFactory.create("b", 2: java.lang.Integer, 1500: java.lang.Integer),
            RowFactory.create("c", 2: java.lang.Integer, 350: java.lang.Integer),
            RowFactory.create("c", 2: java.lang.Integer, 280: java.lang.Integer),
            RowFactory.create("a", 3: java.lang.Integer, 350: java.lang.Integer),
            RowFactory.create("a", 3: java.lang.Integer, 250: java.lang.Integer)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +----+----+----+
        // |店铺|月份|金额|
        // +----+----+----+
        // |a   |1   |150 |
        // |a   |1   |200 |
        // |b   |1   |1000|
        // |b   |1   |800 |
        // |c   |1   |800 |
        // |c   |1   |250 |
        // |b   |1   |250 |
        // |a   |2   |2000|
        // |a   |2   |3000|
        // |b   |2   |1000|
        // |b   |2   |1500|
        // |c   |2   |350 |
        // |c   |2   |280 |
        // |a   |3   |350 |
        // |a   |3   |250 |
        // +----+----+----+
        // 求出每个店铺的当月销售额和累计到当月的总销售额



    }
}
