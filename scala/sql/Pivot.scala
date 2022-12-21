package boluo.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object Pivot {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("年月", "string")
            .add("项目", "string")
            .add("收入", "long")

        val rows = Array(
            RowFactory.create("2018-01", "项目1", 100: java.lang.Long),
            RowFactory.create("2018-01", "项目2", 200: java.lang.Long),
            RowFactory.create("2018-01", "项目3", 300: java.lang.Long),
            RowFactory.create("2018-02", "项目1", 1000: java.lang.Long),
            RowFactory.create("2018-02", "项目2", 2000: java.lang.Long),
            RowFactory.create("2018-03", "项目x", 999: java.lang.Long)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +-------+-----+----+
        // |年月    |项目  |收入|
        // +-------+-----+----+
        // |2018-01|项目1 |100 |
        // |2018-01|项目2 |200 |
        // |2018-01|项目3 |300 |
        // |2018-02|项目1 |1000|
        // |2018-02|项目2 |2000|
        // |2018-03|项目x |999 |
        // +-------+-----+----+

        // 透视
        // 1. 按照不需要转换的字段分组(年月)
        ds.groupBy("`年月`")
            .pivot("`项目`")
            .agg(sum("收入"))
            .show(false)
    }
}
