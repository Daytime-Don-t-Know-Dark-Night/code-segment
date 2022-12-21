package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.{ArrayType, StructType}
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object Transform {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType().add("id", "int")
            .add("array", ArrayType.apply(new StructType()
                .add("name", "string")
                .add("age", "int")
                .add("gender", "string")
                .add("email", "string")))

        val userRow1 = RowFactory.create("dingc", 20: java.lang.Integer, "man", "29511@163.com")
        val userRow2 = RowFactory.create("boluo", 30: java.lang.Integer, "man", "11099@qq.com")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, Array(userRow1, userRow2)),
            RowFactory.create(2: java.lang.Integer, Array(userRow1, userRow2)),
            RowFactory.create(3: java.lang.Integer, Array(userRow1, userRow2))
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+-----------------------------------------------------------------+
        // |id |array                                                            |
        // +---+-----------------------------------------------------------------+
        // |1  |[[dingc, 20, man, 29511@163.com], [boluo, 30, man, 11099@qq.com]]|
        // |2  |[[dingc, 20, man, 29511@163.com], [boluo, 30, man, 11099@qq.com]]|
        // |3  |[[dingc, 20, man, 29511@163.com], [boluo, 30, man, 11099@qq.com]]|
        // +---+-----------------------------------------------------------------+

        // root
        // |-- id: integer (nullable = true)
        // |-- array: array (nullable = true)
        // |    |-- element: struct (containsNull = true)
        // |    |    |-- name: string (nullable = true)
        // |    |    |-- age: integer (nullable = true)
        // |    |    |-- gender: string (nullable = true)
        // |    |    |-- email: string (nullable = true)

        // 需要从array中的每个struct处理, 只保留每个的姓名和年龄两列
        // 可以用udf, 或者explode炸裂开, 保留两列再重新聚合

        ds.withColumn("test1", expr("transform(array(1,2,3), x -> x+1)")).show(false)
        ds.withColumn("array", expr("transform(array, x -> struct(x.name `name`, x.age `age`))"))
            .show(false)

    }
}
