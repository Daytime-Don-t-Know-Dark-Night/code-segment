package boluo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcOptionsInWrite;
import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils;
import org.apache.spark.sql.jdbc.JdbcDialect;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.apache.spark.sql.types.StructType;
import scala.Option;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MySQL {

	public static void replace(Dataset<Row> ds, JdbcOptionsInWrite opts, String where) throws SQLException, ExecutionException, SparkException, InvocationTargetException {

		JdbcDialect dialect = JdbcDialects.get(opts.url());

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
		}

		StructType schema = ds.schema();
		String sql_ = JdbcUtils.getInsertStatement(opts.table(), schema, Option.empty(), true, dialect);

		StringBuilder sb = new StringBuilder();
		String prev = sql_.substring(0, sql_.indexOf("(?"));
		sb.append(prev);

		ds.foreachPartition((rows) -> {

			Connection conn = JdbcUtils.createConnectionFactory(opts).apply();
			conn.setAutoCommit(false);

			PreparedStatement packetStmt = conn.prepareStatement("show global variables like 'max_allowed_packet'");
			PreparedStatement bufferStmt = conn.prepareStatement("show global variables like 'innodb_buffer_pool_size'");
			ResultSet packetRes = packetStmt.executeQuery();
			ResultSet bufferRes = bufferStmt.executeQuery();

			long max_allowed_packet = 4 * 1024 * 1024;
			long buffer_pool_size = 128 * 1024 * 1024;

			while (packetRes.next()) {
				max_allowed_packet = packetRes.getLong("Value");
			}
			while (bufferRes.next()) {
				buffer_pool_size = bufferRes.getLong("Value");
			}

			long partNum = Math.round(1.0 * buffer_pool_size / max_allowed_packet);
			long commitCount = Math.round(0.9 * max_allowed_packet);

			int numFields = schema.fields().length;
			int count1 = 0, count2 = 0;

			while (rows.hasNext()) {
				Row row = rows.next();
				StringBuilder group = new StringBuilder("(");
				for (int i = 0; i < numFields; i++) {
					Object tmp = row.getAs(i);
					group.append(String.format("'%s',", tmp));
				}
				group.delete(group.length() - 1, group.length());
				group.append("),");
				sb.append(group);

				count1++;
				count2++;
				if (count1 % partNum == 0) {
					String ex_sql = StringUtils.chop(sb.toString());
					PreparedStatement preparedStatement = conn.prepareStatement(ex_sql);
					preparedStatement.executeUpdate();
					sb.delete(0, sb.length());
					sb.append(prev);
					count1 = 0;
				}

				long rowLen = Math.round(count2 * group.toString().getBytes().length);
				if (rowLen >= commitCount) {
					System.out.println("执行次数: ");
					conn.commit();
					count2 = 0;
				}
			}

			// 剩余数量不足partNum的
			if (count1 > 0) {
				String ex_sql = StringUtils.chop(sb.toString());
				PreparedStatement preparedStatement = conn.prepareStatement(ex_sql);
				preparedStatement.executeUpdate();
			}
			// conn.commit();

		});
	}
}
