package spark;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.*;

import java.util.List;

public class FlatMap2 {

	public static void main(String[] args) {

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
			List<Row> res = Lists.newArrayList();
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
}
