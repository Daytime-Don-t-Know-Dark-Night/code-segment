package com.boluo.spark.core;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.types.TimestampType$;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2024-07-11 19:44
 * @description
 */
public class Demo05_UDAF {

    // https://www.cnblogs.com/liuyechang/p/17002996.html

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("id", "int")
                .add("ts", "string");

        Row row1 = RowFactory.create(1, "2024-07-01");
        Row row2 = RowFactory.create(1, "2024-07-05");
        Row row3 = RowFactory.create(1, "2024-07-09");
        Row row4 = RowFactory.create(2, "2024-07-03");
        Row row5 = RowFactory.create(2, "2024-07-10");
        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5), scheme);

        ds.withColumn("rk", expr("row_number() over(partition by id order by ts)"))
                .withColumn("java8/date", expr("to_date(ts)"))
                .withColumn("ex", myUdaf.apply(col("java8/date")).over(Window.partitionBy("id").orderBy(desc("rk"))))
                .orderBy("id", "rk")
                .show(false);




    }

    public static UserDefinedAggregateFunction myUdaf = new UserDefinedAggregateFunction() {
        @Override
        public StructType inputSchema() {
            return new StructType()
                    .add("java8/date", "timestamp");
        }

        @Override
        public StructType bufferSchema() {
            return new StructType()
                    .add("last", "timestamp")
                    .add("curr", "timestamp");
        }

        @Override
        public DataType dataType() {
            return ArrayType.apply(TimestampType$.MODULE$);
        }

        @Override
        public boolean deterministic() {
            return false;
        }

        @Override
        public void initialize(MutableAggregationBuffer buffer) {
            buffer.update(0, null);
            buffer.update(1, null);
        }

        @Override
        public void update(MutableAggregationBuffer buffer, Row input) {
            buffer.update(0, buffer.get(1));
            buffer.update(1, input.get(0));
        }

        @Override
        public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object evaluate(Row buffer) {
            LocalDateTime end = Optional.ofNullable(buffer.<Timestamp>getAs(0))
                    .map(Timestamp::toLocalDateTime)
                    .orElse(LocalDateTime.now());
            LocalDateTime start = buffer.<Timestamp>getAs(1).toLocalDateTime();
            long days = Math.max(0L, start.toLocalDate().until(end.toLocalDate(), ChronoUnit.DAYS));
            if (days == 0) {
                return ImmutableList.of(Timestamp.valueOf(start));
            }
            return Stream.iterate(0, i -> i + 1)
                    .limit(days)
                    .map(i -> i == 0
                            ? Timestamp.valueOf(start)
                            : Timestamp.valueOf(start.toLocalDate().plusDays(i).atStartOfDay()))
                    .collect(Collectors.toList());
        }

    };


}
