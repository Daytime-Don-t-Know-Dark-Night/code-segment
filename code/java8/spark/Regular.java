package java8.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2024-08-21 21:53
 * @description
 */
public class Regular {

    private static String jsonStr = "{\"key\": [{\"id\":1,\"name\":\"dingc\",\"num1\":\"13\",\"num2\":-13,\"num3\":13.123456789012345678,\"num4\":-13.14}]}";
    private static Dataset<Row> ds;

    static {
        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("id", "int")
                .add("msg", "string");
        Row row1 = RowFactory.create(1, jsonStr);

        ds = spark.createDataFrame(ImmutableList.of(row1), scheme);
    }

    public static void main(String[] args) {

        ds.withColumn("tmp", expr("from_json(get_json_object(msg,'$.key'), 'array<map<string,string>>')"))
                .selectExpr("tmp")
                .show(false);

        // 精度丢失
        // +-----------------------------------------------------------------------------------------------+
        // |tmp                                                                                            |
        // +-----------------------------------------------------------------------------------------------+
        // |[{id -> 1, name -> dingc, num1 -> 13, num2 -> -13, num3 -> 13.123456789012346, num4 -> -13.14}]|
        // +-----------------------------------------------------------------------------------------------+

        // 正则表达式，匹配 JSON 字符串中的数字（包括正负整数和浮点数）
        String regex = "(?<=:)(-?\\d+(\\.\\d+)?)";
        ds.withColumn("msg", regexp_replace(col("msg"), regex, "\"$1\""))
                .withColumn("tmp", expr("from_json(get_json_object(msg,'$.key'), 'array<map<string,string>>')"))
                .selectExpr("tmp")
                .show(false);

        // +--------------------------------------------------------------------------------------------------+
        // |tmp                                                                                               |
        // +--------------------------------------------------------------------------------------------------+
        // |[{id -> 1, name -> dingc, num1 -> 13, num2 -> -13, num3 -> 13.123456789012345678, num4 -> -13.14}]|
        // +--------------------------------------------------------------------------------------------------+

    }


}
