package spark;

import com.google.common.collect.Lists;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.util.List;

/**
 * @author chao
 * @date 2022/12/22 15:15
 * @desc
 */
public class List2Dataset {

    public static void main(String[] args) {
        // 消息
        String str1 = "message line 1";
        String str2 = "message line 2";
        String str3 = "message line 3";
        String str4 = "message line 4";

        List<String> list = Lists.newArrayList(str1, str2, str3, str4);

        // 转为DataSet<String>
        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        Dataset<String> dsString = spark.createDataset(list, Encoders.STRING());
        dsString.show(false);

    }

}
