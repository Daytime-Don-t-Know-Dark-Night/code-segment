package boluo.sql

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object Join {

    // Apache Spark 支持的7种join类型
    // https://sparkbyexamples.com/spark/different-ways-to-create-a-spark-dataframe/
    // https://blog.csdn.net/u4110122855/article/details/114694753

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
        val ds = order
        val fieldNames = ds.schema.fieldNames
        ds.select(fieldNames.map(col): _*).show(false)
        ds.select(fieldNames.map(name => col(name)): _*).show(false)
        ds.selectExpr(fieldNames: _*).show(false)

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
        leftJoin(order, customer)
        rightJoin(order, customer)
        fullJoin(order, customer)
        semiJoin(order, customer)
        antiJoin(order, customer)
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
        val res = ds1.join(ds2, Seq("customerId"), "left")
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

    def rightJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        // 右连接: right join = right outer join
        val res = ds1.join(ds2, Seq("customerId"), "right")
        res.show(false)
        // +----------+---------+------+-----+
        // |customerId|paymentId|amount|name |
        // +----------+---------+------+-----+
        // |101       |1        |2500  |name1|
        // |103       |3        |500   |name3|
        // |102       |2        |1110  |name2|
        // |102       |4        |400   |name2|
        // |105       |null     |null  |name5|
        // |106       |null     |null  |name6|
        // |104       |null     |null  |name4|
        // +----------+---------+------+-----+
    }

    def fullJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        val res = ds1.join(ds2, Seq("customerId"), "outer")
        res.show(false)
        // +----------+---------+------+-----+
        // |customerId|paymentId|amount|name |
        // +----------+---------+------+-----+
        // |101       |1        |2500  |name1|
        // |103       |3        |500   |name3|
        // |102       |2        |1110  |name2|
        // |102       |4        |400   |name2|
        // |105       |null     |null  |name5|
        // |106       |null     |null  |name6|
        // |104       |null     |null  |name4|
        // +----------+---------+------+-----+
    }

    def semiJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        // left semi join, 只会返回左表中的数据, 会返回左表中匹配右表的数据
        val res = ds1.join(ds2, Seq("customerId"), "left_semi")
        res.show(false)
        // +----------+---------+------+
        // |customerId|paymentId|amount|
        // +----------+---------+------+
        // |101       |1        |2500  |
        // |103       |3        |500   |
        // |102       |2        |1110  |
        // |102       |4        |400   |
        // +----------+---------+------+

        // left semi join 可以用 in/exists 来改写
        ds1.registerTempTable("order")
        ds2.registerTempTable("customer")
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val res2 = spark.sql("select * from order where customerId in (select customerId from customer)")
        // 结果同上
        res2.show(false)
    }

    def antiJoin(ds1: Dataset[Row], ds2: Dataset[Row]) = {
        // left anti join, 只会返回左表中的数据, 但是与 semi_join 相反, 只会返回没有在右表中没有匹配到的左表数据
        val res = ds2.join(ds1, Seq("customerId"), "left_anti")
        res.show(false)
        // +----------+-----+
        // |customerId|name |
        // +----------+-----+
        // |105       |name5|
        // |106       |name6|
        // |104       |name4|
        // +----------+-----+

        // 同理: left anti join 也可以用 not in 来改写
        ds1.registerTempTable("order")
        ds2.registerTempTable("customer")
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val res2 = spark.sql("select * from customer where customerId not in (select customerId from order)")
        // 结果同上
        res2.show(false)
    }

}
