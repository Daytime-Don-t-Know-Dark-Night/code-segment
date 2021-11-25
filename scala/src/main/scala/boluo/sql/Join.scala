package boluo.sql

import org.apache.spark.sql.{Dataset, Row, SparkSession}

object Join {

    // Apache Spark 支持的7种join类型
    // https://sparkbyexamples.com/spark/different-ways-to-create-a-spark-dataframe/
    // TODO https://blog.csdn.net/u4110122855/article/details/114694753

    def main(args: Array[String]): Unit = {
        // 我们有顾客(customer)和订单(order)相关的两张表

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        import spark.implicits._
        val order = spark.sparkContext.parallelize(Seq((1, 101, 2500), (2, 102, 1110), (3, 103, 500), (4, 102, 400)))
            .toDF("paymentId", "customerId", "amount")
        order.show(false)
        // +---------+----------+------+
        // |paymentId|customerId|amount|
        // +---------+----------+------+
        // |1        |101       |2500  |
        // |2        |102       |1110  |
        // |3        |103       |500   |
        // |4        |102       |400   |
        // +---------+----------+------+

        val customer = spark.sparkContext.parallelize(Seq((101, "name1"), (102, "name2"), (103, "name3"), (104, "name4"), (105, "name5"), (106, "name6")))
            .toDF("customerId", "name")
        customer.show(false)
        // +----------+-----+
        // |customerId|name |
        // +----------+-----+
        // |101       |name1|
        // |102       |name2|
        // |103       |name3|
        // |104       |name4|
        // |105       |name5|
        // |106       |name6|
        // +----------+-----+

        // 准备好数据之后, 我们来介绍这些
        innerJoin(order, customer)
        crossJoin(order, customer)
    }

    def innerJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        // 在spark中, 如果没有指定任何join类型, 那么默认就是 inner join, inner join只会返回满足join条件的数据
        val res = ds1.join(ds2, "customerId")
        res.show(false)
        // +----------+---------+------+-----+
        // |customerId|paymentId|amount|name |
        // +----------+---------+------+-----+
        // |101       |1        |2500  |name1|
        // |103       |3        |500   |name3|
        // |102       |2        |1110  |name2|
        // |102       |4        |400   |name2|
        // +----------+---------+------+-----+
    }

    def crossJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        // 笛卡尔积, 左表的每行数据都会跟右表的每行数据进行join, 产生的结果行数为:m*n
        val res = ds1.crossJoin(ds2)
        res.show(false)
        // 4 * 6 = 24行
    }

    def leftJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        // 左连接: left join = left outer join

    }

}
