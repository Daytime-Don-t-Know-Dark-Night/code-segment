package boluo.spark;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.apache.spark.scheduler.SparkListener;
import org.apache.spark.scheduler.SparkListenerTaskEnd;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.delta.util.SetAccumulator;
import org.apache.spark.sql.types.StructType;
import scala.Option;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import static org.apache.spark.sql.functions.*;

public class Listen {

	// Spark 监听器
	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType schema = new StructType()
				.add("date", "date")
				.add("pay_no", "string")
				.add("amount", "double")
				.add("pay_time", "timestamp");

		Row row1 = RowFactory.create(Date.valueOf("2021-11-11"), "001", 1.0, Timestamp.from(Instant.now()));
		Row row2 = RowFactory.create(Date.valueOf("2021-11-11"), "002", 2.0, Timestamp.from(Instant.now()));
		Row row3 = RowFactory.create(Date.valueOf("2021-11-12"), "003", 3.0, Timestamp.from(Instant.now()));
		Row row4 = RowFactory.create(Date.valueOf("2021-11-12"), "004", 4.0, Timestamp.from(Instant.now()));
		Row row5 = RowFactory.create(Date.valueOf("2021-11-13"), "005", 5.0, Timestamp.from(Instant.now()));

		Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5), schema);

		Set<Object> lastSet = Sets.newHashSet();
		SetAccumulator<Object> collectionAc = new SetAccumulator<>();
		collectionAc.register(SparkSession.active().sparkContext(), Option.apply("setAc"), false);
		SparkSession.active().sparkContext().addSparkListener(new SparkListener() {

			public void onTaskEnd(SparkListenerTaskEnd taskEnd) {
				// 上次set中的值 lastSet
				// 本次set中的值 collectionAc.value()

				Set<Object> diffSet = Sets.difference(collectionAc.value(), lastSet);
				if (!diffSet.isEmpty()) {
					for (Object obj : diffSet) {
						System.out.println("监听器检测到差异..." + obj);
					}
				}
			}
		});

		ds.repartition(col("date")).sortWithinPartitions(col("date")).foreachPartition(rows -> {

			Object a = null;
			while (rows.hasNext()) {
				Row row = rows.next();
				Object tmpPartNum = row.getAs("date");
				if (!Objects.equals(tmpPartNum, a)) {
					collectionAc.add(tmpPartNum);
					a = tmpPartNum;
				}
			}
		});

		ds.show(false);
	}
}
