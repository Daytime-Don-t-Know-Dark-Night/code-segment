package spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;

import static org.apache.spark.sql.functions.*;

/**
 * @Author dingc
 * @Date 2022-08-27 10:37
 * @Description
 */
public class Demo1 {

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("id", "int")
                .add("name", "string")
                .add("age", "int")
                .add("email", "string")
                .add("phone", "string")
                .add("image", "string");

        Row row1 = RowFactory.create(1, "dingc", 20, "cding@state.com", "16631086282", "img/dingc");
        Row row2 = RowFactory.create(2, "boluo", 30, "boluo@state.com", "17731086282", "img/boluo");
        Row row3 = RowFactory.create(3, "qidai", 40, "qidai@state.com", "18831086282", "img/qidai");

        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3), scheme);
        ds.show(false);
        // +---+-----+---+---------------+-----------+---------+
        // |id |name |age|email          |phone      |image    |
        // +---+-----+---+---------------+-----------+---------+
        // |1  |dingc|20 |cding@state.com|16631086282|img/dingc|
        // |2  |boluo|30 |boluo@state.com|17731086282|img/boluo|
        // |3  |qidai|40 |qidai@state.com|18831086282|img/qidai|
        // +---+-----+---+---------------+-----------+---------+

        // 自动获取所有的列字段
        final Dataset<Row> ds_ = ds;
        Column[] names = Arrays.stream(ds.schema().fieldNames())
                .map(functions::expr)
                .toArray(n -> new Column[ds_.schema().size()]);

        ds = ds.select(
                concat_ws(",", names).as("res")
        );

        ds.show(false);
        // +------------------------------------------------+
        // |res                                             |
        // +------------------------------------------------+
        // |1,dingc,20,cding@state.com,16631086282,img/dingc|
        // |2,boluo,30,boluo@state.com,17731086282,img/boluo|
        // |3,qidai,40,qidai@state.com,18831086282,img/qidai|
        // +------------------------------------------------+

    }

}
