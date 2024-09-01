package scala2.sql

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
 * @Date 2022-09-14 17:55
 * @Description
 */
object MD5 {

    // MD5不同类型的时间列, 是否影响MD5值
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder.master("local[*]").getOrCreate
        val schema = new StructType()
            .add("id", "int")
            .add("name", "string")
            .add("date", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "dingc", "2022-09-15"),
            RowFactory.create(2: java.lang.Integer, "boluo", "2022-09-16"),
            RowFactory.create(3: java.lang.Integer, "qidai", "2022-09-17")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema).cache()

        // ds.show(false)
        // +---+-----+----------+
        // |id |name |date      |
        // +---+-----+----------+
        // |1  |dingc|2022-09-15|
        // |2  |boluo|2022-09-16|
        // |3  |qidai|2022-09-17|
        // +---+-----+----------+


        ds.withColumn("res", concat_ws(",", col("id"), col("name"), col("date")))
            .select(md5(col("res")).as("md5"))
            .show(false)
        // +--------------------------------+
        // |md5                             |
        // +--------------------------------+
        // |27b21d8d36fe0f62570374b82e4ef293|
        // |cf536208ae1d000f16c75f2a7b9e9673|
        // |1c364e9af514b44f9a7e884e05c3ba80|
        // +--------------------------------+


        ds.withColumn("date", to_date(col("date")))
            .withColumn("res", concat_ws(",", col("id"), col("name"), col("date")))
            .select(md5(col("res")).as("md5"))
            .show(false)
        // +--------------------------------+
        // |md5                             |
        // +--------------------------------+
        // |27b21d8d36fe0f62570374b82e4ef293|
        // |cf536208ae1d000f16c75f2a7b9e9673|
        // |1c364e9af514b44f9a7e884e05c3ba80|
        // +--------------------------------+


        // timestamp type
        ds.withColumn("date", to_timestamp(to_date(col("date"))))
            .withColumn("res", concat_ws(",", col("id"), col("name"), col("date")))
            .select(md5(col("res")).as("md5"))
            .show(false)
        // +--------------------------------+
        // |md5                             |
        // +--------------------------------+
        // |e8b8fa35a8cbd9b4795d704f05d091d8|
        // |1c212af3bb466dc9aa5e6f3b2cd82066|
        // |e369238cf3a3d575704758a7c5907170|
        // +--------------------------------+


        ds.withColumn("date", concat(col("date"), expr("' 00:00:00'")))
            .withColumn("res", concat_ws(",", col("id"), col("name"), col("date")))
            .select(md5(col("res")).as("md5"))
            .show(false)
        // +--------------------------------+
        // |md5                             |
        // +--------------------------------+
        // |e8b8fa35a8cbd9b4795d704f05d091d8|
        // |1c212af3bb466dc9aa5e6f3b2cd82066|
        // |e369238cf3a3d575704758a7c5907170|
        // +--------------------------------+


        // 结论: 不管什么类型, MD5加密都是转为字符串进行加密,
        // 两个不同的类型转成字符串之后数据一样, MD5加密结果也一样
    }

}
