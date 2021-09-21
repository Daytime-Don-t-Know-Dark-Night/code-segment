package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL06 {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("stu_no", "int")
            .add("class", "int")
            .add("score", "int")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, 1901: java.lang.Integer, 90: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, 1901: java.lang.Integer, 90: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, 1901: java.lang.Integer, 83: java.lang.Integer),
            RowFactory.create(4: java.lang.Integer, 1901: java.lang.Integer, 60: java.lang.Integer),
            RowFactory.create(5: java.lang.Integer, 1902: java.lang.Integer, 66: java.lang.Integer),
            RowFactory.create(6: java.lang.Integer, 1902: java.lang.Integer, 23: java.lang.Integer),
            RowFactory.create(7: java.lang.Integer, 1902: java.lang.Integer, 99: java.lang.Integer),
            RowFactory.create(8: java.lang.Integer, 1902: java.lang.Integer, 67: java.lang.Integer),
            RowFactory.create(9: java.lang.Integer, 1902: java.lang.Integer, 87: java.lang.Integer)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +------+-----+-----+
        // |stu_no|class|score|
        // +------+-----+-----+
        // |1     |1901 |90   |
        // |2     |1901 |90   |
        // |3     |1901 |83   |
        // |4     |1901 |60   |
        // |5     |1902 |66   |
        // |6     |1902 |23   |
        // |7     |1902 |99   |
        // |8     |1902 |67   |
        // |9     |1902 |87   |
        // +------+-----+-----+
        // 求每班前三名, 分数一样并列, 同时求出前三名按名次排序的依次的分差
        // 提示: lag(x,y,z) over() 取每个分区内x列的前y个值, 默认值为z

        ds.withColumn("rk", expr("dense_rank() over(partition by class order by score desc)"))
            .where("rk < 4")
            .withColumn("score2", expr("lag(score,1,0) over(partition by class order by score desc)"))
            .withColumn("diff", expr("score-score2"))
            .select(
                "stu_no",
                "class",
                "score",
                "rk",
                "diff"
            )
            .show(false)

    }

}



