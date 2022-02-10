package boluo.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import scala.Tuple3;

import static org.apache.spark.sql.functions.*;

public class Device2 {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder().master("local[*]")
				.config("spark.driver.host", "localhost")
				.getOrCreate();

		Dataset<Tuple3<String, Long, String>> ds = spark.createDataset(ImmutableList.of(
				Tuple3.apply("dev", 101L, "开始"),
				Tuple3.apply("dev", 102L, "开始"),
				Tuple3.apply("dev", 103L, "结束"),
				Tuple3.apply("dev", 104L, "结束"),
				Tuple3.apply("dev", 105L, "开始"),
				Tuple3.apply("dev", 106L, "结束"),
				Tuple3.apply("dev", 107L, "结束")
		), Encoders.tuple(Encoders.STRING(), Encoders.LONG(), Encoders.STRING()));

		ds.show(false);
		// +---+---+----+
		// |_1 |_2 |_3  |
		// +---+---+----+
		// |dev|101|开始|
		// |dev|102|开始|
		// |dev|103|结束|
		// |dev|104|结束|
		// |dev|105|开始|
		// |dev|106|结束|
		// |dev|107|结束|
		// +---+---+----+

		// 一次开始结束区间为一个周期, 一个周期分配一个session, 该示例中有两个周期
		// 开始时间和结束时间都取最早的一条
		// 所以本次结果: [dev1, 101, 103], [dev2, 105, 106]

		ds.withColumn("deviceId", expr("_1"))
				.withColumn("ts", expr("_2"))
				.withColumn("type", expr("_3"))
				.drop("_1", "_2", "_3")
				.show(false);

	}

}
