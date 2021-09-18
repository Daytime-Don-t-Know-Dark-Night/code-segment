package boluo.spark;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType$;

import static org.apache.spark.sql.functions.expr;
import static org.apache.spark.sql.functions.udf;

public class UDFunc {

	public static void main(String[] args) {
		SparkSession spark = SparkSession
				.builder()
				.master("local[*]")
				.getOrCreate();

		String path = "file:///D:/test/t1";
//		Dataset<Long> writeDs = spark.range(0, 5);
//		writeDs.coalesce(1).write()
//				.format("delta")
//				.mode("overwrite")
//				.save(path);

		Dataset<Row> ds = spark.read().format("delta").load(path);
		Dataset<Row> ds0 = ds.withColumn("id2", append("id", "a"));
		ds0.show(false);

		Dataset<Row> ds1 = ds.withColumn("id2", udfAppend.apply(expr("id")));
		ds1.show(false);

		Dataset<Row> ds2 = ds.withColumn("id2", udfAppend2.apply(expr("id"), expr("'a'")));
		ds2.show(false);

		Dataset<Row> ds3 = ds.withColumn("id2", udfAppend3.apply(expr("id"), expr("'a'")));
		ds3.show(false);

		Dataset<Row> ds4 = ds3.withColumn("id3", append2("id", "id2"));
		ds4.show(false);
	}

	public static Column append(String col, String str) {
		return udf((UDF1<Long, String>) s -> {
			return s + str;
		}, DataTypes.StringType).apply(expr(col));
	}

	public static final UserDefinedFunction udfAppend = udf((UDF1<Long, String>) s -> {
		return s + "a";
	}, DataTypes.StringType);

	public static final UserDefinedFunction udfAppend2 = udf((Long s1, String s2) -> {
		return s1 + s2;
	}, DataTypes.StringType);

	public static final UserDefinedFunction udfAppend3 = udf(new UDF2<Long, String, String>() {
		@Override
		public String call(Long a, String s) throws Exception {
			return a + s;
		}
	}, StringType$.MODULE$);

	public static Column append2(String col1, String col2) {
		return udf(new UDF2<Long, String, String>() {
			@Override
			public String call(Long a, String s) throws Exception {
				return a + s;
			}
		}, DataTypes.StringType).apply(expr(col1), expr(col2));
	}

}