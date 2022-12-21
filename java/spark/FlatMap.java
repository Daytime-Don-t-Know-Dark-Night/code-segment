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

import static org.apache.spark.sql.functions.*;

public class FlatMap {

	public static void main(String[] args) {

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
}
