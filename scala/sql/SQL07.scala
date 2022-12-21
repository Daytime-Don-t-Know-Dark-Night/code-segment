package boluo.sql

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._


import scala.collection.JavaConversions

object SQL07 {

    def main(args: Array[String]): Unit = {

        // 1.行转列
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("sid", "int")
            .add("key", "string")
            .add("value", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, 1: java.lang.Integer, "sid", "21"),
            RowFactory.create(2: java.lang.Integer, 1: java.lang.Integer, "name", "dingc1"),
            RowFactory.create(3: java.lang.Integer, 1: java.lang.Integer, "gender", "1"),
            RowFactory.create(4: java.lang.Integer, 1: java.lang.Integer, "email", "110@qq.com"),

            RowFactory.create(5: java.lang.Integer, 2: java.lang.Integer, "sid", "22"),
            RowFactory.create(6: java.lang.Integer, 2: java.lang.Integer, "name", "dingc2"),
            RowFactory.create(7: java.lang.Integer, 2: java.lang.Integer, "gender", "2"),
            RowFactory.create(8: java.lang.Integer, 2: java.lang.Integer, "email", "120@qq.com")
        )

        val rowDs = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        rowDs.show(false)

        // +---+---+------+----------+
        // |id |sid|key   |value     |
        // +---+---+------+----------+
        // |1  |1  |sid   |21        |
        // |2  |1  |name  |dingc1    |
        // |3  |1  |gender|1         |
        // |4  |1  |email |110@qq.com|
        // |5  |2  |sid   |22        |
        // |6  |2  |name  |dingc2    |
        // |7  |2  |gender|2         |
        // |8  |2  |email |120@qq.com|
        // +---+---+------+----------+

        rowDs.groupBy("sid")
            .agg(
                expr("max(if(key = 'sid', value, null)) sid_"),
                expr("max(if(key = 'name', value, null)) name_"),
                expr("max(if(key = 'gender', value, null)) gender_"),
                expr("max(if(key = 'email', value, null)) email_")
            )
            .select(
                "sid_",
                "name_",
                "gender_",
                "email_"
            )
            .show(false)

        // +----+------+-------+----------+
        // |sid_|name_ |gender_|email_    |
        // +----+------+-------+----------+
        // |21  |dingc1|1      |110@qq.com|
        // |22  |dingc2|2      |120@qq.com|
        // +----+------+-------+----------+


    }
}
