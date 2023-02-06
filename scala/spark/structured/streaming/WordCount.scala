package spark.structured.streaming

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author chao
 * @date 2023/2/6 21:23
 * @desc
 */
object WordCount {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession
            .builder()
            .master("local[*]")
            .appName("Structured Streaming Word Count")
            .getOrCreate()

        import spark.implicits._

        // netcat:>>>>>> nc -lp 9999
        val lines = spark.readStream
            .format("socket")
            .option("host", "")
            .option("port", 9999)
            .load()

        // 按照空格切分单词
        val words = lines.as[String].flatMap(_.split(" "))
        words.printSchema()

        // 按照value列进行分组计数
        val wordCounts = words.groupBy("value").count()

        // 开始执行查询并打印单词计数到控制台
        val query = wordCounts.writeStream
            .outputMode("complete")
            .format("console")
            .start()

        query.awaitTermination()

        // -------------------------------------------
        // Batch: 1
        // -------------------------------------------
        // +-----+-----+
        // |value|count|
        // +-----+-----+
        // | ding|    1|
        // | qing|    2|
        // | chao|    1|
        // +-----+-----+

    }
}
