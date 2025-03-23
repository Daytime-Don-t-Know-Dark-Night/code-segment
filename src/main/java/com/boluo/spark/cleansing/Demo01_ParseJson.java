package com.boluo.spark.cleansing;

import com.boluo.utils.SparkUtils;
import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.sql.Timestamp;
import java.util.List;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2025-03-23 19:23
 * @description
 */
public class Demo01_ParseJson {

    private static final String jsonStr = "{\"key\": [{\"id\":1,\"name\":\"dingc\",\"num1\":\"13\",\"num2\":-13,\"num3\":13.123456789012345678,\"num4\":-13.14}]}";
    private static final SparkSession spark = SparkUtils.initialSpark();
    private static final Dataset<Row> ds;

    static {
        StructType schema = new StructType()
                .add("offset", "int")
                .add("partition", "int")
                .add("raw_msg", "string")
                .add("created_timestamp", "timestamp");

        List<Row> list = ImmutableList.of(
                RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis()))
        );

        ds = spark.createDataFrame(list, schema);
    }

    // 将 json 中的字段展开, 数值部分保持不变, 不能丢失数值的精度
    public static void main(String[] args) {
        parseJson(ds);
    }

    public static Dataset<Row> parseJson(Dataset<Row> ds) {
        ds.withColumn("tmp", expr("from_json(get_json_object(raw_msg,'$.key'), 'array<map<string,string>>')"))
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
        Dataset<Row> resDs = ds.withColumn("raw_msg", regexp_replace(col("raw_msg"), regex, "\"$1\""))
                .withColumn("tmp", expr("from_json(get_json_object(raw_msg,'$.key'), 'array<map<string,string>>')"))
                .selectExpr("tmp");

        resDs.show(false);
        // +--------------------------------------------------------------------------------------------------+
        // |tmp                                                                                               |
        // +--------------------------------------------------------------------------------------------------+
        // |[{id -> 1, name -> dingc, num1 -> 13, num2 -> -13, num3 -> 13.123456789012345678, num4 -> -13.14}]|
        // +--------------------------------------------------------------------------------------------------+

        return resDs;
    }

}
