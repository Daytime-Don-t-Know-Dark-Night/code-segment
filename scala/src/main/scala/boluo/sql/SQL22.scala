package boluo.sql

import org.apache.spark.sql.{DataFrame, RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object SQL22 {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[16]").getOrCreate()
        val schema = new StructType()
            .add("id", "int")
            .add("name", "string")

        val rows1 = Array(
            RowFactory.create(1: java.lang.Integer, "zs"),
            RowFactory.create(2: java.lang.Integer, "ls")
        )

        val rows2 = Array(
            RowFactory.create(1: java.lang.Integer, "zs"),
            RowFactory.create(3: java.lang.Integer, "ww")
        )

        @SuppressWarnings("unchecked")
        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema)
        val ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema)

        ds1.show(false)
        ds2.show(false)

        // +---+----+       +---+----+
        // |id |name|       |id |name|
        // +---+----+       +---+----+
        // |1  |zs  |       |1  |zs  |
        // |2  |ls  |       |3  |ww  |
        // +---+----+       +---+----+
        // 求两个数据集的差集

        // select t1.id as id, t1.name as name
        // from t1
        // left join t2
        // on t1.id = t2.id
        // where t2.id is null
        // union
        // select t2.id as id, t2.name as name
        // from t1
        // right join t2
        // on t1.id = t2.id
        // where t1.id is null

        val ds3 = ds1.as("a")
            .join(
                ds2.as("b"),
                expr("a.id = b.id"),
                "left"
            )
            .where("b.id is null")
            .selectExpr(
                "a.id id",
                "a.name name"
            )

        // +---+----+----+----+
        // |id |name|id  |name|
        // +---+----+----+----+
        // |1  |zs  |1   |zs  |
        // |2  |ls  |null|null|
        // +---+----+----+----+

        val ds4 = ds1.as("a")
            .join(
                ds2.as("b"),
                expr("a.id = b.id"),
                "right"
            )
            .where("a.id is null")
            .selectExpr(
                "b.id id",
                "b.name name"
            )

        ds3.union(ds4).show(false)

        // +---+----+
        // |id |name|
        // +---+----+
        // |2  |ls  |
        // |3  |ww  |
        // +---+----+
    }
}
