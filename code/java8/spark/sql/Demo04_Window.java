package java8.spark.sql;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.time.LocalDate;

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
                .add("date", "date")
                .add("score", "int");

        Row row1 = RowFactory.create(1, "dingc", LocalDate.of(2024, 7, 1), 75);
        Row row2 = RowFactory.create(2, "dingc", LocalDate.of(2024, 7, 2), 90);
        Row row3 = RowFactory.create(3, "dingc", LocalDate.of(2024, 7, 3), 80);
        Row row4 = RowFactory.create(4, "dingc", LocalDate.of(2024, 7, 4), 90);
        Row row5 = RowFactory.create(5, "dingc", LocalDate.of(2024, 7, 5), 60);
        Row row6 = RowFactory.create(6, "boluo", LocalDate.of(2024, 7, 1), 60);
        Row row7 = RowFactory.create(7, "boluo", LocalDate.of(2024, 7, 2), 90);
        Row row8 = RowFactory.create(8, "boluo", LocalDate.of(2024, 7, 3), 75);
        Row row9 = RowFactory.create(9, "boluo", LocalDate.of(2024, 7, 4), 80);
        Row row10 = RowFactory.create(10, "boluo", LocalDate.of(2024, 7, 5), 70);

        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10), scheme);
        // ds.show(false);

        // ds.groupBy("name").agg(sum("score")).show(false);

        // ds.withColumn("sum", expr("sum(score) over(partition by name order by date)")).show(false);

        ds.withColumn("sum", expr("sum(score) over(partition by name order by date rows between unbounded preceding and current row)"))
                .show(false);

        ds.withColumn("sum", expr("sum(score) over(partition by name order by date rows between 2 preceding and current row)"))
                .show(false);

    }
}
