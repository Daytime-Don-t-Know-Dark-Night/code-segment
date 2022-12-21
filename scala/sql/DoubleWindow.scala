package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.api.java.UDF2
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.StructType

import scala.collection.{JavaConversions, mutable}
import org.apache.spark.sql.functions._

object DoubleWindow {

    def main(args: Array[String]): Unit = {

        val spark: SparkSession = SparkSession.builder.master("local[*]").getOrCreate
        val schema = new StructType()
            .add("ts", "int")
            .add("app", "string")
            .add("apply", "string")
            .add("log", "string")
            .add("after", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "分区1", "应用1", "A", "1"),
            RowFactory.create(2: java.lang.Integer, "分区1", "应用1", "B", "2"),
            RowFactory.create(3: java.lang.Integer, "分区1", "应用1", "C", "3"),

            RowFactory.create(4: java.lang.Integer, "分区1", "应用2", "A", "1"),
            RowFactory.create(5: java.lang.Integer, "分区1", "应用2", "C", "2"),
            RowFactory.create(6: java.lang.Integer, "分区1", "应用2", "D", "3"),

            RowFactory.create(7: java.lang.Integer, "分区2", "应用1", "甲", "1"),
            RowFactory.create(8: java.lang.Integer, "分区2", "应用1", "乙", "2"),
            RowFactory.create(9: java.lang.Integer, "分区2", "应用1", "丙", "3"),

            RowFactory.create(10: java.lang.Integer, "分区2", "应用2", "甲", "1"),
            RowFactory.create(11: java.lang.Integer, "分区2", "应用2", "乙", "2"),
            RowFactory.create(12: java.lang.Integer, "分区2", "应用2", "丁", "3")
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)
        // +---+-----+-----+---+-----+
        // |ts |app  |apply|log|after|
        // +---+-----+-----+---+-----+
        // |1  |分区1|应用1|A  |1    |
        // |2  |分区1|应用1|B  |2    |
        // |3  |分区1|应用1|C  |3    |
        // |4  |分区1|应用2|A  |1    |
        // |5  |分区1|应用2|C  |2    |
        // |6  |分区1|应用2|D  |3    |
        // |7  |分区2|应用1|甲 |1    |
        // |8  |分区2|应用1|乙 |2    |
        // |9  |分区2|应用1|丙 |3    |
        // |10 |分区2|应用2|甲 |1    |
        // |11 |分区2|应用2|乙 |2    |
        // |12 |分区2|应用2|丁 |3    |
        // +---+-----+-----+---+-----+

        // 要求: app=分区1的分区内, 保留apply中有log='D'的应用数据
        // 要求: app=分区2的分区内, 保留apply中有log='丁'的应用数据

        // 预期结果
        // +---+-----+-----+---+-----+
        // |ts |app  |apply|log|after|
        // +---+-----+-----+---+-----+
        // |4  |分区1|应用2|A  |1    |
        // |5  |分区1|应用2|C  |2    |
        // |6  |分区1|应用2|D  |3    |
        // |10 |分区2|应用2|甲 |1    |
        // |11 |分区2|应用2|乙 |2    |
        // |12 |分区2|应用2|丁 |3    |
        // +---+-----+-----+---+-----+

        // TODO 多次开窗过滤数据
        val ds2 = ds.withColumn("collect_log", collect_list("log")
            .over(Window.partitionBy("app", "apply").orderBy("ts")))
            .withColumn("collect_after", expr("collect_list(after) over(partition by app,apply order by ts)"))
            .withColumn("rk", expr("row_number() over(partition by app,apply order by ts desc)"))
            .where("rk = 1")

        // 注册自定义函数
        spark.udf.register("filter", (app: String, log: mutable.WrappedArray[String]) => {
            app + "boluo"
        })

        ds2.withColumn("fil", expr("filter(app,collect_log)"))
            .show(false)
    }


}
