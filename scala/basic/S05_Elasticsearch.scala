package boluo.basic

import org.apache.spark.sql.SparkSession

object S05_Elasticsearch {

    // Spark连接Elasticsearch
    def main(args: Array[String]): Unit = {

        // 创建sparkConf配置
        val spark = SparkSession.builder()
            .master("local[*]")
            .appName("es")
            .getOrCreate()

        val config = Map(
            "es.nodes.wan.only" -> "true",
            "es.nodes" -> "主机:9200/",
            "es.port" -> "9200",
            "es.net.http.auth.user" -> "账号",
            "es.net.http.auth.pass" -> "密码",
            "es.resource" -> "索引"
        )

        val ds = spark
            .read
            .format("es")
            .options(config)
            .load()

        ds.show(false)

    }

}
