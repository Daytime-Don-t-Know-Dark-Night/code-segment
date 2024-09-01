package java8.spark.core;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType$;
import org.apache.spark.sql.types.StructType;

import java.util.List;

import static org.apache.spark.sql.functions.expr;
import static org.apache.spark.sql.functions.udf;

public class UDFunc {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .getOrCreate();

        StructType schema = new StructType()
                .add("id", "long");

        Row r1 = RowFactory.create(1L);
        Row r2 = RowFactory.create(2L);
        Row r3 = RowFactory.create(3L);
        Row r4 = RowFactory.create(4L);
        Row r5 = RowFactory.create(5L);

        List<Row> list = ImmutableList.of(r1, r2, r3, r4, r5);
        Dataset<Row> ds = spark.createDataFrame(list, schema);
        ds.show(false);

        Dataset<Row> ds0 = ds.withColumn("id2", append("id", "a"));
        ds0.show(false);

        Dataset<Row> ds1 = ds.withColumn("id2", udfAppend.apply(expr("id")));
        ds1.show(false);

        Dataset<Row> ds2 = ds.withColumn("id2", udfAppend2.apply(expr("id"), expr("'a'")));
        ds2.show(false);

        Dataset<Row> ds3 = ds.withColumn("id2", udfAppend3.apply(expr("id"), expr("'a'")));
        ds3.show(false);

        Dataset<Row> ds4 = ds.withColumn("test", expr("named_struct(\"a\", 1, \"b\", named_struct(\"c\", 2))"))
                .withColumn("res", udfAppend4.apply(expr("test")));
        ds4.show(false);

        Dataset<Row> ds5 = ds3.withColumn("id3", append2("id", "id2"));
        ds5.show(false);
    }

    public static Column append(String col, String str) {
        return udf((UDF1<Long, String>) s -> {
            return s + str;
        }, DataTypes.StringType).apply(expr(col));
    }

    public static final UserDefinedFunction udfAppend = udf((UDF1<Long, String>) s -> {
        return s + "a";
    }, DataTypes.StringType);

    public static final UserDefinedFunction udfAppend2 = udf((Long s1, String s2) -> {
        return s1 + s2;
    }, DataTypes.StringType);

    public static final UserDefinedFunction udfAppend3 = udf(new UDF2<Long, String, String>() {
        @Override
        public String call(Long a, String s) throws Exception {
            return a + s;
        }
    }, StringType$.MODULE$);

    public static final UserDefinedFunction udfAppend4 = udf(new UDF1<Row, String>() {

        @Override
        public String call(Row row) throws Exception {
            int res = row.<Row>getAs(1).getInt(0);
            return Integer.toString(res);
        }
    }, StringType$.MODULE$);

    public static Column append2(String col1, String col2) {
        return udf(new UDF2<Long, String, String>() {
            @Override
            public String call(Long a, String s) throws Exception {
                return a + s;
            }
        }, DataTypes.StringType).apply(expr(col1), expr(col2));
    }

}