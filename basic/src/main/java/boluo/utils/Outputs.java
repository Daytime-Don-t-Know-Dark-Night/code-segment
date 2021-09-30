package boluo.utils;

import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import io.delta.tables.DeltaTable;
import org.apache.spark.SparkException;
import org.apache.spark.sql.*;
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils;
import org.apache.spark.sql.jdbc.JdbcDialect;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Function0;
import scala.Option;
import scala.Tuple2;
import scala.collection.JavaConverters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Outputs {

	private static final Logger logger = LoggerFactory.getLogger(Outputs.class);

	/******************************************************************************************************************/

	public static void createTable(Dataset<Row> rows, JdbcOptionsInWrite options, String keys) throws Exception {
		createTable(rows, options, keys, null);
	}

	public static void createTable(Dataset<Row> rows, JdbcOptionsInWrite options, Jdbcs.ConnectConsumer consumer) throws Exception {
		createTable(rows, options, null, consumer);
	}

	public static void createTable(Dataset<Row> rows, JdbcOptionsInWrite options, String keys, Jdbcs.ConnectConsumer consumer) throws Exception {
		JdbcDialect dialect = JdbcDialects.get(options.url());

		String keyStr = "";
		if (!Strings.isNullOrEmpty(keys)) {
			Pattern patternKey = Pattern.compile("(primary\\s+)?key\\s*\\([^)]*\\)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = patternKey.matcher(keys);
			if (matcher.find()) {
				keyStr = "," + keys;
			} else {
				keyStr = ",PRIMARY KEY("
						+ Streams.stream(
						Splitter.on(",").trimResults().omitEmptyStrings()
								.split(keys))
						.map(i -> CharMatcher.anyOf("`").trimFrom(i))
						.map(i -> String.format("`%s`", i))
						.collect(Collectors.joining(","))
						+ ")";
			}
		}

		// 自定义字段类型
		String strSchema = JdbcUtils.schemaString(rows, options.url(), Option.empty());
		if (!options.createTableColumnTypes().isEmpty()) {
			Pattern pat = Pattern.compile("\\s*`?\"?(.+?)`?\"?\\s+(.+)");
			Map<String, String> collect1 = Arrays.stream(strSchema.split(" , "))
					.collect(Collectors.toMap(i -> {
						Matcher matcher = pat.matcher(i);
						Preconditions.checkArgument(matcher.matches(), i);
						return dialect.quoteIdentifier(matcher.group(1));
					}, i -> i));
			Arrays.stream(options.createTableColumnTypes().get().split(","))
					.forEach(i -> {
						Matcher matcher = pat.matcher(i);
						Preconditions.checkArgument(matcher.matches());
						String k = dialect.quoteIdentifier(matcher.group(1));
						String t = matcher.group(2);
						Preconditions.checkArgument(collect1.containsKey(k), String.format("%s not in %s", k, collect1));
						collect1.put(k, k + " " + t);
					});
			strSchema = Joiner.on(",").join(collect1.values());
		}

		String table = options.table();
		String createTableOptions = options.createTableOptions();
		String sql;
		if (options.driverClass().equals("org.postgresql.Driver")) {
			sql = String.format("CREATE TABLE %s (%s%s) %s", table, strSchema, keyStr, createTableOptions);
		} else {
			sql = String.format("CREATE TABLE %s (%s%s) DEFAULT CHARSET=utf8mb4 %s", table, strSchema, keyStr, createTableOptions);
		}
		try (Connection conn = JdbcUtils.createConnectionFactory(options).apply();
			 Statement statement = conn.createStatement()
		) {
			boolean exists = JdbcUtils.tableExists(conn, options);
			if (!exists) {
				statement.setQueryTimeout(options.queryTimeout());
				statement.executeUpdate(sql);
			}
			if (Objects.nonNull(consumer)) {
				consumer.accept(statement, exists);
			}
		}
	}

	/******************************************************************************************************************/

	public static void replace(Dataset<Row> ds, JdbcOptionsInWrite opts, String where) throws SQLException, ExecutionException, SparkException, InvocationTargetException {
		if (ds != null) {
            /*ds.foreachPartition((it) -> {
                new IllegalArgumentException("");
            });*/
			//throw new UndeclaredThrowableException(new IllegalArgumentException(""));
		}

		JdbcDialect dialect = JdbcDialects.get(opts.url());
		long countBefore;
		try (Connection conn = JdbcUtils.createConnectionFactory(opts).apply();
			 Statement statement = conn.createStatement()) {
            /*ResultSet rs = statement.executeQuery(String.format("select count(0) from %s", dialect.quoteIdentifier(opts.table())));
            Preconditions.checkArgument(rs.next());
            countBefore = rs.getLong(1);*/

			if (Objects.isNull(where) || where.equals("1=1")) {
				String sql = String.format("truncate table %s", opts.table());
				statement.executeUpdate(sql);
				System.out.println(sql);
			} else {
				String sql = String.format("delete from %s where %s", opts.table(), where);
				//countBefore -= statement.executeUpdate(sql);
				statement.executeUpdate(sql);
				System.out.println(sql);
			}
		}

//      ds.write().format("jdbc")
//              .mode(SaveMode.Append)
//              .options(opts.parameters())
//              .save();
		StructType schema = ds.schema();
		String sql_ = JdbcUtils.getInsertStatement(opts.table(), schema, Option.empty(), true, dialect);
		String sql;
		if (opts.driverClass().equals("org.postgresql.Driver")) {
			sql = sql_; /*+ " on conflict do update " +
                    Arrays.stream(schema.fieldNames())
                            .map(i -> String.format("set %s=excluded.%s", dialect.quoteIdentifier(i), dialect.quoteIdentifier(i)))
                            .collect(Collectors.joining(","));*/
		} else {
			sql = sql_ + " on duplicate key update " +
					Arrays.stream(schema.fieldNames())
							.map(i -> String.format("%s=values(%s)", dialect.quoteIdentifier(i), dialect.quoteIdentifier(i)))
							.collect(Collectors.joining(","));
		}


//      ds.cache();
//      long countDelta = ds.count();

		StringBuilder sb = new StringBuilder();
		sb.append(sql_);
		String tail = sql_.substring(sql_.indexOf("(?"));

		ds.foreachPartition((rows) -> {

			String sql2 = "insert into xx() values (?,?),(?,?)...(?,?)";

//          Function0<Connection> connectionFactory = JdbcUtils.createConnectionFactory(opts);
//          JdbcUtils.savePartition(connectionFactory,
//                  opts.table(),
//                  JavaConverters.asScalaIteratorConverter(rows).asScala(),
//                  schema, sql, 2000,
//                  dialect, Connection.TRANSACTION_READ_UNCOMMITTED, opts);

			Connection conn = JdbcUtils.createConnectionFactory(opts).apply();
			conn.setAutoCommit(false);
//          PreparedStatement preparedStatement = conn.prepareStatement(sql_);

			int numFields = schema.fields().length;


			int count = 0;

			// TODO 在外面把 ???拼接好, 循环中直接给占位符赋值

			for (int k = 0; k < 5 - 1; k++) {
				sb.append(",").append(tail);
			}
			PreparedStatement preparedStatement = conn.prepareStatement(sb.toString());

			while (rows.hasNext()) {
				Row row = rows.next();

				int i = 0;
				while (i < numFields) {
					preparedStatement.setObject((count * numFields) + i + 1, row.get(i));
					i++;
				}

				count++;

				if (count % 5 == 0) {

					// sb.delete(0, sb.length());
					// sb.append(sql_);

					preparedStatement.addBatch();
					preparedStatement.executeBatch();
					count = 0;
				}
			}
			conn.commit();

		});
//      ds.unpersist();

        /*long countAfter;
        try (Connection conn = JdbcUtils.createConnectionFactory(opts).apply();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(String.format("select count(0) from `%s`", opts.table()));
            Preconditions.checkArgument(rs.next());
            countAfter = rs.getLong(1);
        }*/
		//logger.info("before={},after={},rows={}", countBefore, countAfter, -1);
		//if (countBefore + countDelta != countAfter) {
//      String msg = String.format("%d+%d!=%d", countBefore, countDelta, countAfter);
//      throw new ExecutionException(new IllegalStateException(msg));
		//}
	}
}
