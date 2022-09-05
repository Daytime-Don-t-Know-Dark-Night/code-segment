package boluo.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @Author dingc
 * @Date 2022-09-05 21:54
 * @Description
 */
public class HDFSTest {

    // Spark连接HDFS, avro文件
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();

        // avro文件在HDFS上的路径
        String path = "/path/123.avro";
        Dataset<Row> orc = spark.read()
                .format("avro")
                .option("basePath", "/datalake/.../ORD")
                .load(path);

    }
}
