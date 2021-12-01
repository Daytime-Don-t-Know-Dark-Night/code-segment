package boluo.example

import org.apache.spark.sql.{RowFactory, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

import scala.collection.JavaConversions

object S02_FilterData {

    // 本地账单和阿里对账单对账
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema1 = new StructType()
            .add("no", "string")
            .add("amount", "long")
            .add("account", "string")

        val schema2 = new StructType()
            .add("pay_no", "string")
            .add("pay_amount", "long")

        // 本地账单
        val rows1 = Array(
            RowFactory.create("001", 25: java.lang.Long, "账户1"),
            RowFactory.create("002", 35: java.lang.Long, "账户1"),
            RowFactory.create("003", 45: java.lang.Long, "账户2"),
            RowFactory.create("004", 55: java.lang.Long, "账户2"),
            RowFactory.create("005", 65: java.lang.Long, "账户3"),
            RowFactory.create("006", 75: java.lang.Long, "账户3")
        )

        // 支付宝对账单
        val rows2 = Array(
            RowFactory.create("001", -25: java.lang.Long),
            RowFactory.create("002", -35: java.lang.Long),
            RowFactory.create("003", -45: java.lang.Long)
        )

        val ds1 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema1) // 本地账单
        val ds2 = spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema2) // 支付宝账单

        ds1.show(false)
        ds2.show(false)

        // +---+------+-------+
        // |no |amount|account|
        // +---+------+-------+
        // |001|25    |账户1  |
        // |002|35    |账户1  |
        // |003|45    |账户2  |
        // |004|55    |账户2  |
        // |005|65    |账户3  |
        // |006|75    |账户3  |
        // +---+------+-------+

        // +------+----------+
        // |pay_no|pay_amount|
        // +------+----------+
        // |001   |-25       |
        // |002   |-35       |
        // |003   |-45       |
        // +------+----------+

        var totalDs = ds1.as("s").join(
            ds2.as("t"),
            expr("s.no = t.pay_no"),
            "outer"
        )

        totalDs.show(false)

        // +---+------+-------+------+----------+
        // |no |amount|account|pay_no|pay_amount|
        // +---+------+-------+------+----------+
        // |006|75    |账户3  |null  |null      |
        // |003|45    |账户2  |003   |-45       |
        // |005|65    |账户3  |null  |null      |
        // |001|25    |账户1  |001   |-25       |
        // |004|55    |账户2  |null  |null      |
        // |002|35    |账户1  |002   |-35       |
        // +---+------+-------+------+----------+

        val tmpDs = totalDs.groupBy("account")
            .agg(max("pay_no").as("flag"))
            .where("flag is not null")
            .show(false)

        totalDs = totalDs
            .withColumn("flag", expr("if(pay_no is null, 0, 1)"))
            .withColumn("account_num", expr("sum(flag) over(partition by account)"))

        totalDs.show(false)

        // +---+------+-------+------+----------+----+-----------+
        // |no |amount|account|pay_no|pay_amount|flag|account_num|
        // +---+------+-------+------+----------+----+-----------+
        // |003|45    |账户2  |003   |-45       |1   |1          |
        // |004|55    |账户2  |null  |null      |0   |1          |
        // |006|75    |账户3  |null  |null      |0   |0          |
        // |005|65    |账户3  |null  |null      |0   |0          |
        // |001|25    |账户1  |001   |-25       |1   |2          |
        // |002|35    |账户1  |002   |-35       |1   |2          |
        // +---+------+-------+------+----------+----+-----------+
        totalDs = totalDs.where("account_num > 0")

    }

}
