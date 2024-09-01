package scala2.spark.structured.streaming

import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author chao
 * @date 2023/2/6 23:11
 * @desc
 */
object CreateDsStream {

    // https://zhuanlan.zhihu.com/p/359448701

    def main(args: Array[String]): Unit = {
        func1()
    }

    // 1. File source
    def func1(): Unit = {

        val spark = SparkSession
            .builder()
            .master("local[*]")
            .config("spark.default.parallelism", 2)
            .config("spark.sql.streaming.checkpointLocation", "ds_checkpoint")
            .appName("StructuredStream")
            .getOrCreate()

        spark.sparkContext.setLogLevel("WARN")

        val DATA_PARENT_DIR = "C:/Users/chao/IdeaProjects/code-segment/doc/stream_source"
        val textDF = spark.readStream
            .format("text")
            .option("latestFirst", false) // 是否每次都处理最新的文件
            .option("maxFilesPerTrigger", 5) // 每次触发的最大文件数量
            .option("fileNameOnly", false) // 检查新文件是否只以文件名识别，而不引入路径
            .option("maxFileAge", 3600000) // 默认检查一周以内的文件
            .option("cleanSource", "delete") // 处理完后，由clean线程处理
            .load(s"file:///${DATA_PARENT_DIR}/data/")

        import spark.implicits._

        val words = textDF.as[String].flatMap(_.split(" "))
        val wordCountDF = words
            .groupBy("value")
            .count()
            .coalesce(2)

        wordCountDF.writeStream
            .outputMode(OutputMode.Complete())
            .format("console")
            .start()
            .awaitTermination()

        // Batch: 1
        // -------------------------------------------
        // +---------+-----+
        // |    value|count|
        // +---------+-----+
        // |asdjiasng|    1|
        // |       ad|    1|
        // |     badd|    1|
        // |        a|    1|
        // |     nian|    1|
        // |         |    4|
        // |       as|    2|
        // +---------+-----+

    }
}
