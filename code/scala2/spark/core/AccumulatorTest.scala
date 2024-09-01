package scala2.spark.core

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.util.CollectionAccumulator

import scala.collection.JavaConversions

/**
 * @author chao
 * @date 2022/11/26 16:33
 * @desc
 */
object AccumulatorTest {

    val spark = SparkSession.builder().master("local[*]").getOrCreate()

    // 声明一个累加器, 存放所有年龄大于20的值, 注册
    val ageAccumulator = new CollectionAccumulator[Int]

    def main(args: Array[String]): Unit = {

        val schema = new StructType()
            .add("userId", "string")
            .add("name", "string")
            .add("age", "int")

        val rows = Array(
            RowFactory.create("001", "小明", 15: java.lang.Integer),
            RowFactory.create("002", "小王", 25: java.lang.Integer),
            RowFactory.create("003", "小刚", 35: java.lang.Integer)
        )
        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema).cache()
        ds.show(false)

        SparkSession.active.sparkContext.register(ageAccumulator, "ageAccumulator")

        ageAccumulator.add(0)
        ageAccumulator.add(1)
        ageAccumulator.add(2)
        println("driver: ageAccumulator: " + ageAccumulator)
        println("driver: ageAccumulator registered status: " + ageAccumulator.isRegistered)

        val testUDF = udf(testFunc)
        ds.withColumn("test", testUDF.apply(col("age"))).show(false)
        println("driver: ageAccumulator: " + ageAccumulator.value)
    }

    val testFunc = (age: Int) => {

        // val ageAccumulator: CollectionAccumulator[Int] = spark.sparkContext.collectionAccumulator("ageAccumulator")

        println("executor: ageAccumulator: " + ageAccumulator.value)
        println("executor: ageAccumulator registered status: " + ageAccumulator.isRegistered)
        if (age > 20) {
            println("add age: " + age)
            ageAccumulator.add(age)
        }
        "non"
    }
}
