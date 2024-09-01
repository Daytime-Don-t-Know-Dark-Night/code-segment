package java8.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType$;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.apache.spark.sql.functions.*;

public class UDAFunc {

	// https://www.cnblogs.com/cc11001100/p/9471859.html
	public static void main(String[] args) {

		SparkSession spark = SparkSession
				.builder()
				.master("local[*]")
				.getOrCreate();

		StructType schema = new StructType()
				.add("id", "int");

		Row r1 = RowFactory.create(1);
		Row r2 = RowFactory.create(2);
		Row r3 = RowFactory.create(3);
		Row r4 = RowFactory.create(4);
		Row r5 = RowFactory.create(5);

		List<Row> list = ImmutableList.of(r1, r2, r3, r4, r5);
		Dataset<Row> ds = spark.createDataFrame(list, schema);
		ds.show(false);

		Dataset<Row> ds1 = ds.select(avg("id"));
		ds1.show(false);

	}

	public static Column avg(String col) {
		return new UserDefinedAggregateFunction() {

			@Override
			public StructType inputSchema() {
				return new StructType()
						.add("id", "int");
			}

			@Override
			public StructType bufferSchema() {
				return new StructType()
						.add("sum", "int")
						.add("count", "int");
			}

			@Override
			public DataType dataType() {
				// 返回值数据结构
				return DataTypes.DoubleType;
			}

			@Override
			public boolean deterministic() {
				return false;
			}

			@Override
			public void initialize(MutableAggregationBuffer buffer) {
				buffer.update(0, 0);
				buffer.update(1, 0);
			}

			@Override
			public void update(MutableAggregationBuffer buffer, Row input) {
				if (Objects.isNull(input.get(0))) {
					return;
				}

				int b0 = buffer.getAs(0);
				int b1 = buffer.getAs(1);
				int i1 = input.getAs(0);
				buffer.update(0, b0 + i1);
				buffer.update(1, b1 + 1);
			}

			@Override
			public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
				buffer1.update(0, buffer1.getInt(0) + buffer2.getInt(0));
				buffer1.update(1, buffer1.getInt(1) + buffer2.getInt(1));
			}

			@Override
			public Object evaluate(Row buffer) {

				int b0 = buffer.getAs(0);
				int b1 = buffer.getAs(1);
				return b0 * 1.0 / b1;
			}
		}.apply(expr(col));
	}

}