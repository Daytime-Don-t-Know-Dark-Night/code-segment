package com.boluo.spark.cleansing;

import com.boluo.utils.SparkUtils;
import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2025-03-23 19:31
 * @description
 */
public class Demo01_ParseJsonTest {

    // https://www.cnblogs.com/itcq1024/p/18536711

    private static final SparkSession spark = SparkUtils.initialSpark();
    private static final StructType schema = new StructType()
            .add("offset", "int")
            .add("partition", "int")
            .add("raw_msg", "string")
            .add("created_timestamp", "timestamp");


    @Test
    public void func1() {
        String jsonStr = "{\"key\": [{\"num\":\"13\"}]}";
        List<Row> list = ImmutableList.of(RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis())));
        Dataset<Row> ds = spark.createDataFrame(list, schema).cache();
        Dataset<Row> resDs = Demo01_ParseJson.parseJson(ds);
        resDs = resDs.withColumn("tmp", explode(col("tmp")));
        Assert.assertEquals(resDs.selectExpr("tmp.num").first().getString(0), "13");
    }

    @Test
    public void func2() {
        String jsonStr = "{\"key\": [{\"num\":\"-13\"}]}";
        List<Row> list = ImmutableList.of(RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis())));
        Dataset<Row> ds = spark.createDataFrame(list, schema).cache();
        Dataset<Row> resDs = Demo01_ParseJson.parseJson(ds);
        resDs = resDs.withColumn("tmp", explode(col("tmp")));
        Assert.assertEquals(resDs.selectExpr("tmp.num").first().getString(0), "-13");
    }

    @Test
    public void func3() {
        String jsonStr = "{\"key\": [{\"num\":\"13.123489\"}]}";
        List<Row> list = ImmutableList.of(RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis())));
        Dataset<Row> ds = spark.createDataFrame(list, schema).cache();
        Dataset<Row> resDs = Demo01_ParseJson.parseJson(ds);
        resDs = resDs.withColumn("tmp", explode(col("tmp")));
        Assert.assertEquals(resDs.selectExpr("tmp.num").first().getString(0), "13.123489");
    }

    @Test
    public void func4() {
        String jsonStr = "{\"key\": [{\"num\":\"-13.123489\"}]}";
        List<Row> list = ImmutableList.of(RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis())));
        Dataset<Row> ds = spark.createDataFrame(list, schema).cache();
        Dataset<Row> resDs = Demo01_ParseJson.parseJson(ds);
        resDs = resDs.withColumn("tmp", explode(col("tmp")));
        Assert.assertEquals(resDs.selectExpr("tmp.num").first().getString(0), "-13.123489");
    }

    @Test
    public void func5() {
        String jsonStr = "{\"key\": [{\"num\":\"13.12348912375349514269873\"}]}";
        List<Row> list = ImmutableList.of(RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis())));
        Dataset<Row> ds = spark.createDataFrame(list, schema).cache();
        Dataset<Row> resDs = Demo01_ParseJson.parseJson(ds);
        resDs = resDs.withColumn("tmp", explode(col("tmp")));
        Assert.assertEquals(resDs.selectExpr("tmp.num").first().getString(0), "13.12348912375349514269873");
    }

    @Test
    public void func6() {
        String jsonStr = "{\"key\": [{\"num\":\"-13.12348912375349514269873\"}]}";
        List<Row> list = ImmutableList.of(RowFactory.create(1, 0, jsonStr, new Timestamp(System.currentTimeMillis())));
        Dataset<Row> ds = spark.createDataFrame(list, schema).cache();
        Dataset<Row> resDs = Demo01_ParseJson.parseJson(ds);
        resDs = resDs.withColumn("tmp", explode(col("tmp")));
        Assert.assertEquals(resDs.selectExpr("tmp.num").first().getString(0), "-13.12348912375349514269873");
    }


}
