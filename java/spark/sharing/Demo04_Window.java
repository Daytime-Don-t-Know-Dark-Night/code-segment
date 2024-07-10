package spark.sharing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.util.CollectionAccumulator;
import org.apache.spark.util.LongAccumulator;
import scala.Option;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2024-07-10 23:53
 * @description
 */
public class Demo04_Window {



    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("id", "int")
                .add("name", "string")
                .add("day", "int")
                .add("score", "int");

        Row row1 = RowFactory.create(1, "dingc", 1, 75);
        Row row2 = RowFactory.create(2, "dingc", 2, 90);
        Row row3 = RowFactory.create(3, "dingc", 3, 80);
        Row row4 = RowFactory.create(4, "dingc", 4, 90);
        Row row5 = RowFactory.create(5, "dingc", 5, 60);
        Row row6 = RowFactory.create(6, "boluo", 1, 60);
        Row row7 = RowFactory.create(7, "boluo", 2, 90);
        Row row8 = RowFactory.create(8, "boluo", 3, 75);
        Row row9 = RowFactory.create(9, "boluo", 4, 80);
        Row row10 = RowFactory.create(10, "boluo", 5, 70);

        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10), scheme);
        ds.show(false);
    }
}
