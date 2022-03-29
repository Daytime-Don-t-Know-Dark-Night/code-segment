package boluo.spark

import com.google.common.util.concurrent.Uninterruptibles
import org.apache.spark.sql.{RowFactory, SparkSession}
import com.mongodb.client.model.Collation
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType

import java.util.concurrent.TimeUnit
import scala.collection.JavaConversions

/**
 * @Author dingc
 * @Date 2022/3/29 20:52
 */
object Stage {

    def func1(spark: SparkSession): Unit = {
        // shuffle是划分DAG中stage的标识,
        val schema = new StructType()
            .add("id", "int")
            .add("name", "string")

        val rows1 = Array(
            RowFactory.create(1: java.lang.Integer, "菠萝"),
            RowFactory.create(2: java.lang.Integer, "吹雪"),
            RowFactory.create(3: java.lang.Integer, "吹雪")
        )

        val rows2 = Array(
            RowFactory.create(1: java.lang.Integer, "桃花"),
            RowFactory.create(2: java.lang.Integer, "公主1"),
            RowFactory.create(2: java.lang.Integer, "公主2")
        )

        var ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema)
        var ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema)

        ds1 = ds1.withColumn("name", concat(expr("name"), expr("'_'")))
            .filter(row => row.getInt(0) <= 2)

        ds2 = ds2.groupBy("id")
            .agg(
                min("name")
            )

        ds1.show()
        ds2.show()
        ds1.as("a").join(ds2.as("b"),
            expr("a.id = b.id"),
            "outer"
        ).show(false)

        Uninterruptibles.sleepUninterruptibly(5000, TimeUnit.SECONDS)

    }

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        func1(spark)
    }

}
