package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL08 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("userid", "int")
            .add("subject", "string")
            .add("score", "double")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, "语文", 90: java.lang.Double),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, "数学", 92: java.lang.Double),
            RowFactory.create(3: java.lang.Integer, 1: java.lang.Integer, "英语", 80: java.lang.Double),
            RowFactory.create(4: java.lang.Integer, 2: java.lang.Integer, "语文", 88: java.lang.Double),
            RowFactory.create(5: java.lang.Integer, 2: java.lang.Integer, "数学", 90: java.lang.Double),
            RowFactory.create(6: java.lang.Integer, 2: java.lang.Integer, "英语", 75.5: java.lang.Double),
            RowFactory.create(7: java.lang.Integer, 3: java.lang.Integer, "语文", 70: java.lang.Double),
            RowFactory.create(8: java.lang.Integer, 3: java.lang.Integer, "数学", 85: java.lang.Double),
            RowFactory.create(9: java.lang.Integer, 3: java.lang.Integer, "英语", 90: java.lang.Double),
            RowFactory.create(10: java.lang.Integer, 3: java.lang.Integer, "政治", 82: java.lang.Double)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+------+-------+-----+
        // |id |userid|subject|score|
        // +---+------+-------+-----+
        // |1  |1     |语文   |90.0 |
        // |2  |1     |数学   |92.0 |
        // |3  |1     |英语   |80.0 |
        // |4  |2     |语文   |88.0 |
        // |5  |2     |数学   |90.0 |
        // |6  |2     |英语   |75.5 |
        // |7  |3     |语文   |70.0 |
        // |8  |3     |数学   |85.0 |
        // |9  |3     |英语   |90.0 |
        // |10 |3     |政治   |82.0 |
        // +---+------+-------+-----+

        // 实现行转列, 结果如下
        // +------+----+----+----+----+-----+
        // |userid|语文 |数学|英语 |政治 |total|
        // +------+----+----+----+----+-----+
        // |1     |90.0|92.0|80.0|0.0 |262.0|
        // |3     |70.0|85.0|90.0|82.0|327.0|
        // |2     |88.0|90.0|75.5|0.0 |253.5|
        // +------+----+----+----+----+-----+

        ds.groupBy("userid")
            .agg(
                expr("sum(case when subject = '语文' then score else 0 end)").as("语文"),
                expr("sum(case when subject = '数学' then score else 0 end)").as("数学"),
                expr("sum(case when subject = '英语' then score else 0 end)").as("英语"),
                expr("sum(case when subject = '政治' then score else 0 end)").as("政治")
            )
            .withColumn("total", expr("`语文` + `数学` + `英语` + `政治`"))
            .show(false)
    }
}
