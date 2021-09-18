package boluo.delta

import io.delta.tables.DeltaTable
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType

import scala.collection.JavaConversions

object DeltaLakeMergeTest {

    def main(args: Array[String]): Unit = {

        // 存储功能有bug, 不使用存储功能
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val type1 = new StructType()
            .add("sid", "int")
            .add("name", "string")

        val rows1 = Array(
            RowFactory.create(1: java.lang.Integer, "dingc"),
            RowFactory.create(2: java.lang.Integer, "boluo")
        )

        val rows2 = Array(
            RowFactory.create(2: java.lang.Integer, "dc2"),
            RowFactory.create(3: java.lang.Integer, "dc3")
        )

        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), type1)
        val ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), type1)

        ds1.show(false)
        ds2.show(false)

        DeltaTable.forPath(spark, "have a bug")
            .as("s")
            .merge(
                ds2.as("c"),
                "s.sid = c.sid")
            .whenMatched
            .updateExpr(Map(
                "s.name" -> "c.name"
            ))
            .whenNotMatched()
            .insertAll()
            .execute()

    }
}
