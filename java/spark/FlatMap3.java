package spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.StructType;

import java.util.Iterator;
import java.util.stream.Stream;

import static org.apache.spark.sql.functions.*;

/**
 * @Author dingc
 * @Date 2022/2/15 20:53
 */
public class FlatMap3 {

	public static void main(String[] args) {

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
