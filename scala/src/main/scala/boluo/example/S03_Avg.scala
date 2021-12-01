package boluo.example

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, Row, RowFactory, SparkSession, functions}

import java.sql.Date
import scala.collection.JavaConversions

object S03_Avg {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("时间", "date")
            .add("设备id", "string")
            .add("设备类型", "string")
            .add("订单号", "string")
            .add("订单金额", "long")

        val rows = Array(
            RowFactory.create(Date.valueOf("2020-01-01"), "dev1", "type1", "ord1", 3: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev2", "type1", "ord2", 4: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev2", "type1", "ord3", 5: java.lang.Long),
            RowFactory.create(Date.valueOf("2020-01-01"), "dev3", "type2", "ord4", 5: java.lang.Long)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        val resDs = avg(ds)


        // 结果验证
        val schema2 = new StructType()
            .add("时间", "date")
            .add("设备类型", "string")
            .add("订单数", "long")
            .add("总金额", "long")
            .add("台次平均数", "double")

        val rows2 = Array(
            RowFactory.create(Date.valueOf("2020-01-01"), "type1", 3: java.lang.Long, 12: java.lang.Long, 1.5: java.lang.Double),
            RowFactory.create(Date.valueOf("2020-01-01"), "type2", 1: java.lang.Long, 5: java.lang.Long, 1.0: java.lang.Double)
        )
        val standardRes = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema2)
        assert(resDs.equals(standardRes))

    }

    def avg(ds: Dataset[Row]): Dataset[Row] = {

        ds.show(false)
        // +----------+------+--------+------+--------+
        // |时间      |设备id|设备类型|订单号|订单金额|
        // +----------+------+--------+------+--------+
        // |2020-01-01|dev1  |type1   |ord1  |3       |
        // |2020-01-01|dev2  |type1   |ord2  |4       |
        // |2020-01-01|dev2  |type1   |ord3  |5       |
        // |2020-01-01|dev3  |type2   |ord4  |5       |
        // +----------+------+--------+------+--------+

        // 第一步求出 每个设备每日的订单数, 按时间, 设备类型, 设备id分组
        val ds2 = ds.groupBy("设备类型", "时间", "设备id")
            .agg(
                count("订单号").as("订单数"),
                sum("订单金额").as("每天每台订单总金额")
            )

        ds2.show(false)

        return ds2.groupBy("时间", "设备类型")
            .agg(
                functions.avg("订单数").as("平均每台"),
                sum("每天每台订单总金额").as("总金额"),
                sum("订单数").as("订单数")
            )

        // 需要输出结果
        // 按时间和设备类型分组, 外加订单数列, 总金额列, 台次平均数列

        // 台次: 一台设备一天产生10个订单, 则日台次为10
        // 台次列: 按时间和设备类型分组下, 所有设备台次的平均数

        // return ds;
        // throw UnsupportedOperationException

    }

}
