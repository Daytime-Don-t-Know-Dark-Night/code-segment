package boluo.sql

import com.google.common.base.Objects
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, DataTypes, StructType}
import org.apache.spark.sql.{Dataset, Row, RowFactory, SparkSession}

import scala.collection.JavaConversions

/**
 * @author chao
 * @date 2022/11/22 19:04
 * @desc
 */
object ExceptDemo2 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema = new StructType()
            .add("userId", "string")
            .add("name", "string")
            .add("age", "string")

        val rows1 = Array(
            RowFactory.create("001", "小明", "10"),
            RowFactory.create("002", "小李", "11"),
            RowFactory.create("003", "小红", "12"),
            RowFactory.create("004", "小刚", "12")
        )

        val rows2 = Array(
            RowFactory.create("001", "小明", "10"),
            RowFactory.create("002", "小李", "11"),
            RowFactory.create("003", "小王", "12"),
            RowFactory.create("004", "小刚", "13")
        )


        var ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema).cache()
        var ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema).cache()

        ds1 = named_struct(ds1, "source")
        ds2 = named_struct(ds2, "target")

        ds1.show(false)
        ds2.show(false)

        val dsJoin = ds1.as("src").join(
            ds2.as("tar"),
            expr("src.userId = tar.userId"),
            "full"
        ).where("src.source != tar.target")
        dsJoin.show(false)

        // ArrayType.apply(tmpSchema) 代表的是UDF返回值的类型
        var tmpSchema = new StructType().add("tag", DataTypes.StringType)
        val sc = dsJoin.schema.apply("source").dataType.asInstanceOf[StructType]
        for (s <- sc) {
            tmpSchema = tmpSchema.add(s)
        }
        val compareUDF = udf(compare, ArrayType.apply(tmpSchema))

        var res = dsJoin.select(
            compareUDF.apply(col("source"), col("target")) as "res"
        )
        res.show(false)

        res = res.withColumn("res", explode(col("res")))
        val names = res.schema.apply("res").dataType.asInstanceOf[StructType].fieldNames
        val name = names.toStream.map(n => "res." + n).toArray
        res.selectExpr(name: _*).show(false)
    }


    def named_struct(ds: Dataset[Row], tag: String): Dataset[Row] = {
        // named_struct("a", 1, "b", 2, "c", 3)
        val array: Array[String] = ds.columns.toStream.map(name => String.format("'%s',%s", name, name)).toArray
        val inner = String.join(",", JavaConversions.asJavaCollection(array))
        ds.withColumn(tag, expr("named_struct(" + inner + ")"))
    }


    def compare: (Row, Row) => Seq[Row] = (sourceRow: Row, targetRow: Row) => {
        assert(sourceRow.schema.size == targetRow.schema.size)
        var sourceList: List[Any] = List("source")
        var targetList: List[Any] = List("target")
        for (i <- sourceRow.schema.indices) {
            if (Objects.equal(sourceRow.get(i), targetRow.get(i))) {
                sourceList = sourceList :+ null
                targetList = targetList :+ null
            } else {
                sourceList = sourceList :+ sourceRow.get(i)
                targetList = targetList :+ targetRow.get(i)
            }
        }

        val tmpSourceRow = RowFactory.create(JavaConversions.asJavaCollection(sourceList).toArray: _*)
        val tmpTargetRow = RowFactory.create(JavaConversions.asJavaCollection(targetList).toArray: _*)
        Seq.apply(tmpSourceRow, tmpTargetRow)
    }
}
