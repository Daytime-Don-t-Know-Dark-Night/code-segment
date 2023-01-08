package spark

import org.apache.spark.sql.SparkSession

/**
 * @author chao
 * @date 2023/1/8 18:16
 * @desc
 */
object ConnectMongo {

    // Spark连接Mongo数据库
    def main(args: Array[String]): Unit = {

        val config = Map(
            "mongo.uri" -> "mongodb://账号:密码@主机",
            "mongo.db" -> "数据库名",
            "mongo.collection" -> "表名"
        )

        // 创建sparkConf配置
        val spark = SparkSession.builder()
            .master("local[*]")
            .appName("MongoSparkConnectorIntro")
            .getOrCreate()

        val ds = spark.read
            .option("uri", config("mongo.uri"))
            .option("database", config("mongo.db"))
            .option("collection", config("mongo.collection"))
            .format("mongo")
            .load()

        ds.show(false)

    }

}
