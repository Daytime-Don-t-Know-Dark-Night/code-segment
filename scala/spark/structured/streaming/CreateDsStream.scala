package spark.structured.streaming

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @author chao
 * @date 2023/2/6 23:11
 * @desc
 */
object CreateDsStream {

    // https://zhuanlan.zhihu.com/p/359448701

    def main(args: Array[String]): Unit = {

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

        val DATA_PARENT_DIR = "E:/05.git_project/bigdata_code/structuredstreaming_demo"


    }
}
