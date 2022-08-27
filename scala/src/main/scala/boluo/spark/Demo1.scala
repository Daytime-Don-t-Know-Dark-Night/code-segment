package boluo.spark

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType

import scala.collection.JavaConversions

/**
 * @Author dingc
 * @Date 2022-08-26 22:36
 * @Description
 */
object Demo1 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()

        val schema = new StructType()
            .add("id", "int")
            .add("name", "string")
            .add("age", "int")
            .add("email", "string")
            .add("phone", "string")
            .add("image", "string")

        val rows = Array(
            RowFactory.create(1: java.lang.Integer, "dingc", 20: java.lang.Integer, "cding@state.com", "16631086282", "/img/dingc"),
            RowFactory.create(2: java.lang.Integer, "boluo", 30: java.lang.Integer, "boluo@state.com", "17731086282", "/img/boluo"),
            RowFactory.create(3: java.lang.Integer, "qidai", 40: java.lang.Integer, "qidai@state.com", "18831086282", "/img/qidai")
        )

        var ds = spark.createDataFrame(JavaConversions.seqAsJavaList(rows), schema)
        ds.show(false)

        // +---+-----+---+---------------+-----------+----------+
        // |id |name |age|email          |phone      |image     |
        // +---+-----+---+---------------+-----------+----------+
        // |1  |dingc|20 |cding@state.com|16631086282|/img/dingc|
        // |2  |boluo|30 |boluo@state.com|17731086282|/img/boluo|
        // |3  |qidai|40 |qidai@state.com|18831086282|/img/qidai|
        // +---+-----+---+---------------+-----------+----------+

        // 因为要把所有的字段全部拼接成为一个字段, 所以要先取到所有的字段名
        val names = ds.schema.fieldNames
            .toStream
            .map(name => expr(name))
            .toArray

        ds.select(concat_ws(",", names: _*)).show(false)
        // +-------------------------------------------------+
        // |concat_ws(,, id, name, age, email, phone, image) |
        // +-------------------------------------------------+
        // |1,dingc,20,cding@state.com,16631086282,/img/dingc|
        // |2,boluo,30,boluo@state.com,17731086282,/img/boluo|
        // |3,qidai,40,qidai@state.com,18831086282,/img/qidai|
        // +-------------------------------------------------+

    }

}
