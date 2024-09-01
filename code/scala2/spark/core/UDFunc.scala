package scala2.spark.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**
 * @author chao
 * @date 2022/11/23 21:21
 * @desc
 */
object UDFunc {

    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        import spark.implicits._
        val columns = Seq("Seqno", "Quote")

        val data = Seq(
            ("1", "Be the change that you wish to see in the world"),
            ("2", "Everyone thinks of changing the world, but no one thinks of changing himself."),
            ("3", "The purpose of our lives is to be happy.")
        )
        val ds = data.toDF(columns: _*)
        ds.show(false)

        val convertUDF = udf(convertCase)
        ds.select(col("Seqno"), convertUDF(col("Quote")).as("Quote")).show(false)

    }

    // 首字母大写
    val convertCase = (strQuote: String) => {
        val arr = strQuote.split(" ")
        arr.map(f => f.substring(0, 1).toUpperCase + f.substring(1, f.length)).mkString(" ")
    }

}
