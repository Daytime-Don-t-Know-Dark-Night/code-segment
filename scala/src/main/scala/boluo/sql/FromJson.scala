package boluo.sql

import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Uninterruptibles
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

import java.util.concurrent.TimeUnit
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

/**
 * @Author dingc
 * @Date 2022-09-19 23:23
 * @Description
 */
object FromJson {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("orderNo", "string")

        val rows = Array(
            RowFactory.create("001"),
            RowFactory.create("002"),
            RowFactory.create("003")
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)

        ds.withColumn("far_leg", expr("named_struct(\"a\", null, \"b\", null, \"c\", null)"))
            .show(false)

    }

}
