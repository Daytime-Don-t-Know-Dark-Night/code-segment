package spark;

import com.clearspring.analytics.util.Lists;
import com.google.common.collect.ImmutableList;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.StructType;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.spark.sql.functions.*;

public class FlatMap {

    public static void main(String[] args) {
        func1();
        func2();
        func3();
    }

    public static void func1() {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType schema = new StructType()
                .add("name", "string")
                .add("email", "string");

        Row row1 = RowFactory.create("dingc", "110");
        Row row2 = RowFactory.create("dingc", "120");
        Row row3 = RowFactory.create("dingc", "130");

        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3), schema);
        ds.show(false);

        // +-----+-----+
        // |name |email|
        // +-----+-----+
        // |dingc|110  |
        // |dingc|120  |
        // |dingc|130  |
        // +-----+-----+
        // 复制一份, 对两份分别做处理

        Dataset<Row> ds1 = ds.withColumn("flag", array(expr("1"), expr("2")))
                .withColumn("flag", explode(col("flag")));
        ds1.show(false);

        Dataset<Row> ds2 = ds.flatMap(new FlatMapFunction<Row, Row>() {
            @Override
            public Iterator<Row> call(Row row) throws Exception {
                List<Row> list = Lists.newArrayList();
                {

                    String name = row.getAs("name");
                    String email = row.getAs("email");

                    name = new StringBuilder(name).append("1").toString();
                    email = new StringBuilder(email).append("1").toString();

                    Row tmpRow = RowFactory.create(name, email);
                    list.add(tmpRow);
                }
                {
                    String name = row.getAs("name");
                    String email = row.getAs("email");

                    name = new StringBuilder(name).append("2").toString();
                    email = new StringBuilder(email).append("2").toString();

                    Row tmpRow = RowFactory.create(name, email);
                    list.add(tmpRow);
                }

                return list.iterator();
            }
        }, RowEncoder.apply(new StructType()
                .add("name", "string")
                .add("email", "string")
        ));
        ds2.show(false);

    }

    public static void func2() {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType schema = new StructType()
                .add("no", "string")
                .add("a", "string")
                .add("b", "string")
                .add("c", "string");

        Row row1 = RowFactory.create("1001", "1", "2", "3");
        Row row2 = RowFactory.create("1002", "1", "2", "3");

        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2), schema);
        ds.show(false);

        StructType schema1 = ds.schema();
        ds.flatMap((FlatMapFunction<Row, Row>) row -> {
            String no = row.getAs("no");
            int len = schema1.length() - 1;
            List<Row> res = com.google.common.collect.Lists.newArrayList();
            for (int i = 0; i < len; i++) {
                res.add(RowFactory.create(no, schema1.apply(i + 1).name(), row.getAs(i + 1)));
            }
            return res.iterator();
        }, RowEncoder.apply(new StructType()
                .add("no", "string")
                .add("字母", "string")
                .add("内容", "string")
        )).show(false);

    }

    public static void func3() {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("deviceId", "string")
                .add("ts", "string")
                .add("type", "string");

        Row row1 = RowFactory.create("dev1", "101", "RS");
        Row row2 = RowFactory.create("dev1", "102", "RF");
        Row row3 = RowFactory.create("dev1", "103", "RQ");
        Row row4 = RowFactory.create("dev1", "104", "RQ");
        Row row5 = RowFactory.create("dev1", "105", "RF");
        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5), scheme);

        ds.show(false);
        // +--------+---+----+
        // |deviceId|ts |type|
        // +--------+---+----+
        // |dev1    |101|RS  |
        // |dev1    |102|RF  |
        // |dev1    |103|RQ  |
        // |dev1    |104|RQ  |
        // |dev1    |105|RF  |
        // +--------+---+----+

        // 要求: 将RS转换为开始, RF转换为结束, 其余的过滤掉
        ds.where("type = 'RS' or type = 'RF'")
                .withColumn("type", expr("if(type='RS','开始','结束')"))
                .show(false);

        ds.flatMap(new FlatMapFunction<Row, Row>() {
                    @Override
                    public Iterator<Row> call(Row row) {
                        String deviceId = row.getAs("deviceId");
                        String ts = row.getAs("ts");
                        String type = row.getAs("type");
                        if (!type.equals("RS") && !type.equals("RF")) {
                            return Stream.<Row>of().iterator();
                        }
                        String state = type.equals("RS") ? "开始" : "结束";
                        return Stream.of(RowFactory.create(deviceId, ts, state)).iterator();
                    }
                }, RowEncoder.apply(new StructType()
                        .add("deviceId", "string")
                        .add("ts", "string")
                        .add("type", "string")))
                .show(false);

    }

}
