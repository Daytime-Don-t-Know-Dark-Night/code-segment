package com.boluo.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.boluo.jdbc.JdbcDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @Author dingc
 * @Date 2021/11/30 19:53
 */
public class JDBCTransaction {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(JdbcDemo.class);

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1.加载驱动
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 2.建立连接
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boluo?characterEncoding=UTF-8&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&user=root&password=root", "root", "root");
		return conn;
	}

	private static void func1() throws SQLException, ClassNotFoundException {
		try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
			conn.setAutoCommit(false);
			// 如果关闭自动提交事务, 执行完下面这句话, 数据库中的值并不会发生改变, 开启自动提交事务, 执行完之后数据库中的值会立即改变
			statement.executeUpdate("update test set `name` = 'boluo' where `id` = 1");
			ResultSet res = statement.executeQuery("select * from test");
			while (res.next()) {
				System.out.println(res.getString("name"));
			}
		}
	}

	// TODO https://www.w3cschool.cn/jdbc/czow1myo.html
	// https://www.yiibai.com/jdbc/jdbc-transactions.html
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		func1();
	}
}
