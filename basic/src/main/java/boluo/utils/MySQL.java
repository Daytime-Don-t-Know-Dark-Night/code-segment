package boluo.utils;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils;
import org.apache.spark.sql.jdbc.JdbcDialect;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MySQL {

	private static final Logger logger = LoggerFactory.getLogger(MySQL.class);

	public static void replace(Dataset<Row> ds, JdbcOptionsInWrite opts, String where) throws SQLException, ExecutionException, SparkException, InvocationTargetException {

		JdbcDialect dialect = JdbcDialects.get(opts.url());
		long max_allowed_packet = 4 * 1024 * 1024;
		long buffer_pool_size = 128 * 1024 * 1024;

		try (Connection conn = JdbcUtils.createConnectionFactory(opts).apply();
			 Statement statement = conn.createStatement()) {
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

			try (ResultSet packetRes = statement.executeQuery("show global variables like 'max_allowed_packet'")) {
				while (packetRes.next()) {
					max_allowed_packet = packetRes.getLong("Value");
				}
			}

			try (ResultSet bufferRes = statement.executeQuery("show global variables like 'innodb_buffer_pool_size'")) {
				while (bufferRes.next()) {
					buffer_pool_size = bufferRes.getLong("Value");
				}
			}
		}

		StructType schema = ds.schema();
		String sql_ = JdbcUtils.getInsertStatement(opts.table(), schema, Option.empty(), true, dialect);

		// sql拼接时不使用''的类型
		List<DataType> specialType = ImmutableList.of(DataTypes.BooleanType);

		StringBuilder sb = new StringBuilder();
		String prev = sql_.substring(0, sql_.indexOf("(?"));
		sb.append(prev);

		long partNum = Math.round(0.7 * max_allowed_packet);
		long commitCount = buffer_pool_size / max_allowed_packet;

		ds.foreachPartition((rows) -> {

			try (Connection conn = JdbcUtils.createConnectionFactory(opts).apply();
				 Statement statement = conn.createStatement()) {
				conn.setAutoCommit(false);

				int numFields = schema.fields().length;
				int partCount = 0, executeCount = 0, sumCount = 0;

				while (rows.hasNext()) {
					Row row = rows.next();
					StringBuilder group = new StringBuilder("(");
					for (int i = 0; i < numFields; i++) {
						DataType type = schema.apply(i).dataType();

						if (row.isNullAt(i)) {
							// null值处理
							group.append("null,");
						} else if (specialType.contains(type)) {
							// 判断该类型数据是否需要''
							Object tmp = row.getAs(i);
							group.append(String.format("%s,", tmp));
						} else if (type == DataTypes.StringType) {
							// 如果该类型为字符串类型且包含', 则对'进行转义
							String tmp = row.getAs(i);
							group.append(String.format("'%s',", tmp.replaceAll("'", "''")));
						} else {
							Object tmp = row.getAs(i);
							group.append(String.format("'%s',", tmp));
						}
					}
					group.delete(group.length() - 1, group.length());
					group.append("),");
					sb.append(group);

					partCount++;
					if (sb.length() >= partNum) {
						String ex_sql = StringUtils.chop(sb.toString());
						statement.executeUpdate(ex_sql);
						sb.delete(0, sb.length());
						sb.append(prev);
						partCount = 0;
						executeCount++;
						logger.info("execute执行次数: {}", sumCount++);
					}

					if (executeCount >= commitCount) {
						logger.info("commit执行时间: {}", Instant.now());
						conn.commit();
						executeCount = 0;
					}
				}

				// 剩余数量不足partNum的
				if (partCount > 0) {
					String ex_sql = StringUtils.chop(sb.toString());
					statement.executeUpdate(ex_sql);
				}
				conn.commit();
			}

		});
	}
}

