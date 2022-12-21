package boluo.sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, DataTypes}

object JsonParse {

    def main(args: Array[String]): Unit = {

        val spark: SparkSession = SparkSession.builder.master("local[*]").getOrCreate

        // 生成: json_object
        val objectDs = spark.sqlContext.sql("select to_json(named_struct('name','dingc','age',20)) as json")
            .withColumn("json2", expr("to_json(named_struct('name','dingc','age',20,'user',to_json(map('no',007))))"))
        objectDs.show(false)

        // 解析: json_object
        objectDs.withColumn("name", expr("get_json_object(json,'$.name')"))
            .show(false)

        objectDs.select(
            expr("json_tuple(json,'name','age')")
        ).show(false)

        // 生成: json_array
        val arrayDs = spark.sqlContext.sql("select to_json(array(map('name','dingc','age',20),map('name','qidai','no',007))) as json")
        arrayDs.show(false)

        // 解析: json_array
        arrayDs
            .withColumn("json", explode(from_json(col("json"), ArrayType.apply(DataTypes.StringType))))
            // .withColumn("json", expr("explode(from_json(json,'array<string>'))"))
            .show(false)

    }
}
