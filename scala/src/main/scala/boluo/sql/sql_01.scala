package boluo.sql

import org.apache.spark.sql.SparkSession

object sql_01 {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val path = "./data/delta/sql_01"

        // val ds = spark.range(0, 5)
        // ds.write.format("delta").save("./data/delta/sql_01")

        val ds = spark.read.format("delta").load(path)
        ds.show(false)

    }
}
