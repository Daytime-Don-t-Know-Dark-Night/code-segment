package boluo;

import boluo.utils.MySQL;
import com.google.common.collect.Lists;
import org.apache.spark.SparkException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.collection.immutable.Map$;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ImportMySQL {

	public static void main(String[] args) throws SQLException, SparkException, ExecutionException, InvocationTargetException {

		SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
		StructType schema = new StructType()
				.add("date", "string")
				.add("time", "timestamp")
				.add("amount", "double")
				.add("pay_no", "long");

		List<Row> list = Lists.newArrayList();

		for (int i = 0; i < 10; i++) {

			UUID uuid = UUID.randomUUID();
			long id = -1 * uuid.getLeastSignificantBits();

			double randomAmount = Math.random() * 10;
			double amount = Double.parseDouble(String.format("%.2f", randomAmount));

			Row row = RowFactory.create(LocalDate.now().toString(), Timestamp.valueOf(LocalDateTime.now()), amount, id);
			list.add(row);
		}

		Dataset<Row> ds = spark.createDataFrame(list, schema);
		ds.show(false);

		String uri = "jdbc:mysql://localhost:3306/test_boluo?characterEncoding=UTF-8&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&user=root&password=root";

		Map<String, String> props = new Hashtable<>();
		props.put(JDBCOptions.JDBC_DRIVER_CLASS(), "com.mysql.cj.jdbc.Driver");
		props.put(JDBCOptions.JDBC_TABLE_NAME(), "table_name");
		props.put(JdbcOptionsInWrite.JDBC_CREATE_TABLE_COLUMN_TYPES(), String.join(",", "date varchar(64)"));

		Seq<Tuple2<String, String>> tuple2Seq = JavaConverters.mapAsScalaMapConverter(props).asScala().toSeq();
		JdbcOptionsInWrite opt = new JdbcOptionsInWrite((scala.collection.immutable.Map<String, String>) Map$.MODULE$.apply(tuple2Seq));
		// TODO create table
		MySQL.replace(ds, opt, "1=1");

	}

}
