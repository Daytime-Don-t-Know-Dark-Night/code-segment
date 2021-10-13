package boluo.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.expr;

public class Device {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType scheme = new StructType()
				.add("rev", "string")
				.add("项目名称", "string")
				.add("门店名称", "string")
				.add("设备编号", "string")
				.add("金额", "double");

		Row row1 = RowFactory.create("1", "运营体1", "门店1", "设备1", 1.0);
		Row row2 = RowFactory.create("2", "运营体1", "门店2", "设备2", 1.0);
		Row row3 = RowFactory.create("3", "运营体2", "门店1", "设备1", 1.0);

		Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3), scheme);
		ds.show(false);

		// +---+--------+--------+--------+----+
		// |rev|项目名称|门店名称|设备编号|金额|
		// +---+--------+--------+--------+----+
		// |1  |运营体1 |门店1   |设备1   |1.0 |
		// |2  |运营体1 |门店2   |设备2   |1.0 |
		// |3  |运营体2 |门店1   |设备1   |1.0 |
		// +---+--------+--------+--------+----+
		// 按设备分组, 每组设备金额求和, 再保留该组设备rev最大的一条的信息


		ds = ds.withColumn("sum_", expr("sum(`金额`) over(partition by `设备编号` order by rev)"));
		ds = ds.withColumn("max_", expr("max(rev) over(partition by `设备编号`)"));
		ds.where("rev = max_")
				.selectExpr(
						"rev",
						"`项目名称`",
						"`门店名称`",
						"`设备编号`",
						"sum_"
				)
				.show(false);
	}
}
