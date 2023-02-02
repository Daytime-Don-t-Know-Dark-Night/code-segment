package spark.core

import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
 * @author chao
 * @date 2023/1/23 16:39
 * @desc
 */
object Output {

    def main(args: Array[String]): Unit = {

        val ds = RDD.getDs
        ds.show(false)

        func1(ds)
    }

    def func1(ds: Dataset[Row]): Unit = {

    }


}
