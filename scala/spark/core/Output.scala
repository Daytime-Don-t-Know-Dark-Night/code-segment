package spark.core

import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
 * @author chao
 * @date 2023/1/23 16:39
 * @desc
 */
object Output {

    val baseOutputPath = "C:\\Users\\chao\\IdeaProjects\\code-segment\\doc\\spark\\output"

    def main(args: Array[String]): Unit = {
        val ds = RDD.getDs
        func2(ds)
    }

    // 1. 默认存储为parquet文件
    def func1(ds: Dataset[Row]): Unit = {
        ds.show(false)
        ds.coalesce(1)
            .write
            .save(baseOutputPath + "/parquet")
    }

    // 2. 存储为csv格式
    def func2(ds: Dataset[Row]): Unit = {
        ds.show(false)
        ds.coalesce(1)
            .write
            .option("inferSchema", "false")
            .option("header", "true")
            .option("charset", "UTF-8")
            .csv(baseOutputPath + "/csv")
    }


}
