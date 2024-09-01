package scala2.sql

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
 * @Date 2022-10-14 22:50
 * @Description
 */
object ExceptDemo {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("userId", "string")
            .add("name", "string")

        val rows1 = Array(
            RowFactory.create("001", "小明"),
            RowFactory.create("002", "小李"),
            RowFactory.create("003", "小红")
        )

        val rows2 = Array(
            RowFactory.create("001", "小明"),
            RowFactory.create("002", "小李"),
            RowFactory.create("003", "小王")
        )

        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema).cache()
        val ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema).cache()

        ds1.show(false)
        // 第一个DataFrame中的数据如下:
        // +------+----+
        // |userId|name|
        // +------+----+
        // |001   |小明|
        // |002   |小李|
        // |003   |小红|
        // +------+----+

        ds2.show(false)
        // 第二个DataFrame中的数据如下:
        // +------+----+
        // |userId|name|
        // +------+----+
        // |001   |小明|
        // |002   |小李|
        // |003   |小王|
        // +------+----+

        /** 1. 使用 except找出差异部分 ********************************************************************************* */

        val res1 = ds1.exceptAll(ds2)
        val res2 = ds2.exceptAll(ds1)
        // 差异部分是res1和res2之和
        val res = res1.unionByName(res2)
        res.show(false)
        // 结果为差异部分:　
        // +------+----+
        // |userId|name|
        // +------+----+
        // |003   |小红|
        // |003   |小王|
        // +------+----+


        /** 2. 使用 full join 找出差异部分 ***************************************************************************** */

        // 把ds1和ds2 full join 连接起来
        val joinDs = ds1.as("a").join(
            ds2.as("b"),
            expr("a.userId = b.userId"),
            "full"
        )
        joinDs.show(false)
        // 连接之后结果为:
        // +------+----+------+----+
        // |userId|name|userId|name|
        // +------+----+------+----+
        // |003   |小红|003   |小王|
        // |001   |小明|001   |小明|
        // |002   |小李|002   |小李|
        // +------+----+------+----+

        // 找出 a.name != b.name 的即为差异的结果
        joinDs.where("a.name != b.name").show(false)
        // 差异结果如下:
        // +------+----+------+----+
        // |userId|name|userId|name|
        // +------+----+------+----+
        // |003   |小红|003   |小王|
        // +------+----+------+----+

        Uninterruptibles.sleepUninterruptibly(5000, TimeUnit.SECONDS)

    }


}
