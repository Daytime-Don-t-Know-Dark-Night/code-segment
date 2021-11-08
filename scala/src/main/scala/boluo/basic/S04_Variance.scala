package boluo.basic

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, Row, RowFactory, SparkSession, functions}

import java.sql.Date
import scala.collection.JavaConversions

object S04_Variance {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[8]").getOrCreate()
        val schema = new StructType()
            .add("时间", "date")
            .add("设备id", "string")
            .add("设备类型", "string")
            .add("订单号", "string")
            .add("订单金额", "long")

        val rows = Array(
            RowFactory.create(Date.valueOf("2019-12-31"), "dev1", "type1", "ord0", 2: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev1", "type1", "ord1", 3: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev2", "type1", "ord2", 4: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev2", "type1", "ord3", 5: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev3", "type2", "ord4", 5: java.lang.Long)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        val resDs = variance(ds)
        resDs.show(false)
    }


    def variance(ds: Dataset[Row]): Dataset[Row] = {

        // ds.show(false)
        // +----------+------+--------+------+--------+
        // |时间      |设备id|设备类型|订单号|订单金额|
        // +----------+------+--------+------+--------+
        // |2019-12-31|dev1  |type1   |ord0  |2       |
        // |2020-01-01|dev1  |type1   |ord1  |3       |
        // |2020-01-01|dev2  |type1   |ord2  |4       |
        // |2020-01-01|dev2  |type1   |ord3  |5       |
        // |2020-01-01|dev3  |type2   |ord4  |5       |
        // +----------+------+--------+------+--------+

        // 第一步: 按时间, 设备类型, 设备id聚合, 得到台次(每台设备的日台次), sum(金额)
        val no1 = ds.withColumn("时间", to_timestamp(to_date(col("时间"))))
            .groupBy("时间", "设备类型", "设备id")
            .agg(
                countDistinct(expr("if(`设备类型` is not null, `订单号`, null)")).as("台次"),
                sum("订单金额").as("金额")
            )

        // 第二步: 计算每日前七天的(平均数, 中位数, 方差, 偏度, 峰度)
        val day7 = "range between interval '6' day preceding and current row"
        val no2 = no1.withColumn("台次(平均数)", expr(s"mean(`台次`) over(partition by `设备类型` order by `时间` $day7)"))
            .withColumn("台次(中位数)", expr(s"percentile_approx(`台次`,0.5,100) over(partition by `设备类型` order by `时间` $day7)"))
            .withColumn("台次(方差)", expr(s"var_pop(`台次`) over(partition by `设备类型` order by `时间` $day7)"))
            .withColumn("台次(偏度)", expr(s"skewness(`台次`) over(partition by `设备类型` order by `时间` $day7)"))
            .withColumn("台次(峰度)", expr(s"kurtosis(`台次`) over(partition by `设备类型` order by `时间` $day7)"))

        // 第三步: 求出每日,每种设备类型的总金额, 总订单
        val no3 = no2.groupBy("时间", "设备类型", "台次(平均数)", "台次(中位数)", "台次(方差)", "台次(偏度)", "台次(峰度)")
            .agg(
                sum("台次").as("订单数"),
                sum("金额").as("金额")
            )

        // 第四步: 计算出前七日的订单数及金额
        val no4 = no3
            .withColumn("订单数(7日)", expr(s"sum(`订单数`) over(partition by `设备类型` order by `时间` $day7)"))
            .withColumn("金额(7日)", expr(s"sum(`金额`) over(partition by `设备类型` order by `时间` $day7)"))
            .withColumn("时间", to_date(col("时间"), "yyyy-MM-dd"));

        return no4
    }

}

