package boluo.spark

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType

import scala.collection.JavaConversions

object SaveDemo extends App{

    val spark = SparkSession.builder().master("local[*]").getOrCreate()

    val schema = new StructType()
        .add("name", "string")

    val rows = Array(
        RowFactory.create("dingc"),
        RowFactory.create("boluo"),
        RowFactory.create("qidai")
    )

    var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
    ds.show(false)

    ds.coalesce(1)
        .write
        .format("parquet")
        .save("C:\\save")

}
