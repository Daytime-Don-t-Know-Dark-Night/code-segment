package spark;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.concurrent.TimeUnit;

import static org.apache.spark.sql.functions.*;

/**
 * @Author dingc
 * @Date 2022/2/27 13:22
 */
public class DataSkew2 {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType scheme = new StructType()
				.add("时间", "string")
				.add("订单号", "string")
				.add("备注", "string");

		Row row1 = RowFactory.create("2022-01-01", "001", "备注1");
		Row row2 = RowFactory.create("2022-01-01", "002", "备注2");
		Row row3 = RowFactory.create("2022-01-02", "003", "备注3");
		Row row4 = RowFactory.create("2022-01-02", "004", "备注4");
		Row row5 = RowFactory.create("2022-01-03", "005", "备注5");
		Row row6 = RowFactory.create("2022-01-03", "006", "备注6");
		Row row7 = RowFactory.create("2022-01-03", "007", "备注7");
		Row row8 = RowFactory.create("2022-01-03", "008", "备注8");
		Row row9 = RowFactory.create("2022-01-03", "009", "备注9");
		Row row10 = RowFactory.create("2022-01-03", "010", "备注10");
		Row row11 = RowFactory.create("2022-01-03", "011", "备注11");
		Row row12 = RowFactory.create("2022-01-03", "012", "备注12");
		Row row13 = RowFactory.create("2022-01-03", "013", "备注13");
		Row row14 = RowFactory.create("2022-01-03", "014", "备注14");
		Row row15 = RowFactory.create("2022-01-03", "015", "备注15");
		Row row16 = RowFactory.create("2022-01-03", "016", "备注16");
		Row row17 = RowFactory.create("2022-01-04", "017", "备注17");
		Row row18 = RowFactory.create("2022-01-04", "018", "备注18");
		Row row19 = RowFactory.create("2022-01-05", "019", "备注19");

		Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5, row6, row7, row8, row9,
				row10, row11, row12, row13, row14, row15, row16, row17, row18, row19), scheme);

		// TODO 这里使用第二种处理方式, 全部打散
		ds = ds.withColumn("tmp", expr("int(rand)*6"))
				.repartition(col("时间"), col("tmp"));

	}
}
