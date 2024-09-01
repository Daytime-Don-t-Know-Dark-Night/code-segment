package scala2.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.{JavaConversions, mutable}

object WindowFunc {

    // https://bbs.huaweicloud.com/blogs/detail/289870
    def main(args: Array[String]): Unit = {

        // partition by... order by...
        // distribute by... sort by...

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("mid", "int")
            .add("cid", "int")
            .add("score", "int")
            .add("date", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, 10: java.lang.Integer, "2021-08-24"),
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, 20: java.lang.Integer, "2021-08-25"),
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, 30: java.lang.Integer, "2021-08-26"),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, 40: java.lang.Integer, "2021-08-24"),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, 50: java.lang.Integer, "2021-08-25"),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, 60: java.lang.Integer, "2021-08-26"),
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, 20: java.lang.Integer, "2021-08-27"),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, 40: java.lang.Integer, "2021-08-27")
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds = ds.withColumn("s", struct(expr("null")))
        ds.show(false)

        // 排序开窗函数 row_number() over, rank() over(), dense_rank() over()
        // 分组排序
        ds.withColumn("a", expr("rank() over(partition by mid,cid order by score desc)"))
            .withColumn("b", expr("dense_rank() over(partition by mid,cid order by score desc)"))
            .withColumn("c", expr("row_number() over(partition by mid,cid order by score desc)"))
            .show(false)


        // 聚合开窗函数
        // 1.不加任何条件, 求和该字段所有值
        ds.withColumn("sum1", expr("sum(score) over()"))
            .withColumn("sum2", expr("sum(score) over(partition by mid order by date)"))
            .show(false)


        mutable.WrappedArray
    }
}
