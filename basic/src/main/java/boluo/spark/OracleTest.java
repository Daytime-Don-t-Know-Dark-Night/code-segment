package boluo.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @Author dingc
 * @Date 2022-09-05 21:44
 * @Description
 */
public class OracleTest {

    // Spark连接Oracle数据库
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        Dataset<Row> orc = spark.read()
                .format("jdbc")
                .option("url", "jdbc:oracle:thin:@127.0.0.1:1521/oral")
                .option("dbtable", "boluo")
                .option("user", "gti")
                .option("password", "123456")
                .load();

        orc.show(false);

    }

}
