package boluo.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

public class DataSkew {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType scheme = new StructType()
				.add("date", "string")
				.add("订单号", "string");

		Row row1 = RowFactory.create("2022-01-01", "001");
		Row row2 = RowFactory.create("2022-01-02", "002");
		Row row3 = RowFactory.create("2022-01-03", "003");
		Row row4 = RowFactory.create("2022-01-03", "004");
		Row row5 = RowFactory.create("2022-01-03", "005");
		Row row6 = RowFactory.create("2022-01-03", "006");
		Row row7 = RowFactory.create("2022-01-03", "007");
		Row row8 = RowFactory.create("2022-01-03", "008");
		Row row9 = RowFactory.create("2022-01-03", "009");

		Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5, row6, row7, row8, row9), scheme);
		ds.show(false);
		// +----------+------+
		// |date      |订单号|
		// +----------+------+
		// |2022-01-01|001   |
		// |2022-01-02|002   |
		// |2022-01-03|003   |
		// |2022-01-03|004   |
		// |2022-01-03|005   |
		// |2022-01-03|006   |
		// |2022-01-03|007   |
		// |2022-01-03|008   |
		// |2022-01-03|009   |
		// +----------+------+

		// 为每个订单号

	}
}
