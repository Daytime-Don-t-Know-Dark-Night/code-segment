package scala2.spark.structured.streaming

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author chao
 * @date 2023/2/6 21:49
 * @desc
 */
object ConsumerKafka {

    def main(args: Array[String]): Unit = {
        func1()
    }

    def func1(): Unit = {

        val spark: SparkSession = SparkSession
            .builder()
            .master("local[*]")
            .appName("KafkaSource")
            .getOrCreate()

        val df = spark.readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "hadoop1:9092, hadoop2:9092, hadoop3:9092")
            .option("subscribe", "topic1")
            .load()
            .selectExpr("cast(value as string)")

        df.writeStream
            .format("console")
            .outputMode("update")
            .option("truncate", value = false)
            .start
            .awaitTermination()

    }

}
