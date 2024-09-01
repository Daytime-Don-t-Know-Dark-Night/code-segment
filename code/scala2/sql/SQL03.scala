package scala2.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.{DataType, StructType}

import scala.collection.JavaConversions

object SQL03 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("userid", "string")
            .add("month", "string")
            .add("visits", "int")

        val rows = Array(
            RowFactory.create("A", "2015-01", 5: java.lang.Integer),
            RowFactory.create("A", "2015-01", 15: java.lang.Integer),
            RowFactory.create("B", "2015-01", 5: java.lang.Integer),
            RowFactory.create("A", "2015-01", 8: java.lang.Integer),
            RowFactory.create("B", "2015-01", 25: java.lang.Integer),
            RowFactory.create("A", "2015-01", 5: java.lang.Integer),
            RowFactory.create("A", "2015-02", 4: java.lang.Integer),
            RowFactory.create("A", "2015-02", 6: java.lang.Integer),
            RowFactory.create("B", "2015-02", 10: java.lang.Integer),
            RowFactory.create("B", "2015-02", 5: java.lang.Integer),
            RowFactory.create("A", "2015-03", 16: java.lang.Integer),
            RowFactory.create("A", "2015-03", 22: java.lang.Integer),
            RowFactory.create("B", "2015-03", 23: java.lang.Integer),
            RowFactory.create("B", "2015-03", 10: java.lang.Integer),
            RowFactory.create("B", "2015-03", 1: java.lang.Integer)
        )

        val ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        //  +------+-------+------+
        //  |userid|month  |visits|
        //  +------+-------+------+
        //  |A     |2015-01|5     |
        //  |A     |2015-01|15    |
        //  |B     |2015-01|5     |
        //  |A     |2015-01|8     |
        //  |B     |2015-01|25    |
        //  |A     |2015-01|5     |
        //  |A     |2015-02|4     |
        //  |A     |2015-02|6     |
        //  |B     |2015-02|10    |
        //  |B     |2015-02|5     |
        //  |A     |2015-03|16    |
        //  |A     |2015-03|22    |
        //  |B     |2015-03|23    |
        //  |B     |2015-03|10    |
        //  |B     |2015-03|1     |
        //  +------+-------+------+
        // 求每个用户截止到每月为止的最大单月访问次数和累计到该月的总访问次数


    }

}
