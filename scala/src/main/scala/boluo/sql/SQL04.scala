package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType

import scala.collection.JavaConversions

object SQL04 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("uid", "int")
            .add("dt", "string")
            .add("login_status", "int")

        val rows = Array(

            RowFactory.create(1: java.lang.Integer, "2019-07-11", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-12", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-13", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-14", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-15", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-16", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-17", 1: java.lang.Integer),
            RowFactory.create(1: java.lang.Integer, "2019-07-18", 1: java.lang.Integer),

            RowFactory.create(2: java.lang.Integer, "2019-07-11", 1: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-12", 1: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-13", 0: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-14", 1: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-15", 1: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-16", 0: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-17", 1: java.lang.Integer),
            RowFactory.create(2: java.lang.Integer, "2019-07-18", 0: java.lang.Integer),

            RowFactory.create(3: java.lang.Integer, "2019-07-11", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-12", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-13", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-14", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-15", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-16", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-17", 1: java.lang.Integer),
            RowFactory.create(3: java.lang.Integer, "2019-07-18", 1: java.lang.Integer)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        //  +---+----------+------------+
        //  |uid|dt        |login_status|
        //  +---+----------+------------+
        //  |1  |2019-07-11|1           |
        //  |1  |2019-07-12|1           |
        //  |1  |2019-07-13|1           |
        //  |1  |2019-07-14|1           |
        //  |1  |2019-07-15|1           |
        //  |1  |2019-07-16|1           |
        //  |1  |2019-07-17|1           |
        //  |1  |2019-07-18|1           |
        //  |2  |2019-07-11|1           |
        //  |2  |2019-07-12|1           |
        //  |2  |2019-07-13|0           |
        //  |2  |2019-07-14|1           |
        //  |2  |2019-07-15|1           |
        //  |2  |2019-07-16|0           |
        //  |2  |2019-07-17|1           |
        //  |2  |2019-07-18|0           |
        //  |3  |2019-07-11|1           |
        //  |3  |2019-07-12|1           |
        //  |3  |2019-07-13|1           |
        //  |3  |2019-07-14|1           |
        //  +---+----------+------------+
        // 求连续登录7天的总人数

        ds.where("login_status = 1")
            .selectExpr("uid", "dt", "row_number() over(distribute by uid sort by dt) rm")
            .show(false)
    }
}
