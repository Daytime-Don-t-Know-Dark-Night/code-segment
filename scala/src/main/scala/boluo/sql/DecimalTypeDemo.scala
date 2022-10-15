package boluo.sql

import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Uninterruptibles
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

import java.util.concurrent.TimeUnit
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

/**
 * @Author dingc
 * @Date 2022-10-15 16:47
 * @Description
 */
object DecimalTypeDemo {

    def main(args: Array[String]): Unit = {

        // 有一列数据在Oracle中是Number类型, Spark读取为DataFrame之后, 在DataFrame中为DecimalType(38,5)类型
        // 在Oracle中: 2.5, 3, 14, 8.7, 有小数点显示小数点后面数字, 小数点后为0的话不显示
        // 在DataFrame中: 2.500, 3.000, 14.000, 8.700, 2.003...
        // 去掉小数点后面多余的0

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType().add("num", "decimal(38,5)")

        val rows = Array(
            RowFactory.create(new java.math.BigDecimal(2.0)),
            RowFactory.create(new java.math.BigDecimal(3)),
            RowFactory.create(new java.math.BigDecimal(4.07)),
            RowFactory.create(new java.math.BigDecimal(0.002))
        )
        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)
        // +-------+
        // |num    |
        // +-------+
        // |2.00000|
        // |3.00000|
        // |4.07000|
        // |0.00200|
        // +-------+

        // 期望结果: 2, 3, 4.07, 0.002
        ds = ds.withColumn("num", expr("cast(cast(num as float) as string)"))
        // +-----+
        // |num  |
        // +-----+
        // |2.0  |
        // |3.0  |
        // |4.07 |
        // |0.002|
        // +-----+

        ds.withColumn("num", expr("regexp_replace(num,'.0$','')"))
            .show(false)

    }

}
