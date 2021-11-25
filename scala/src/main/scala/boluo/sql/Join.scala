package boluo.sql

import org.apache.spark.sql.SparkSession

object Join {

    // Apache Spark 支持的7种join类型
    // https://sparkbyexamples.com/spark/different-ways-to-create-a-spark-dataframe/
    // TODO https://blog.csdn.net/u4110122855/article/details/114694753

    def main(args: Array[String]): Unit = {
        // 我们有顾客(customer)和订单(order)相关的两张表
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val order = spark.sparkContext.parallelize(Seq((1, 101, 2500), (2, 102, 1110), (3, 103, 500), (4, 102, 400)))
    }
}
