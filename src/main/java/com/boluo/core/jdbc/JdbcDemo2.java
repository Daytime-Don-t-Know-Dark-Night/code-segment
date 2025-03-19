package com.boluo.core.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcDemo2 {
	// 动态查询
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(JdbcDemo2.class);

	static Connection conn;
	static PreparedStatement ps;
	static ResultSet res;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boluo", "root", "root");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		ps = conn.prepareStatement("select * from user where id = ?");
		ps.setInt(1, 2);
		res = ps.executeQuery();

		while (res.next()) {
			Integer id = res.getInt("id");
			String name = res.getString("name");
			Integer age = res.getInt("age");
			String email = res.getString("email");
			ObjectNode obj = mapper.createObjectNode()
					.put("id", id)
					.put("name", name)
					.put("age", age)
					.put("email", email);
			logger.info("用户信息为: {}", obj);
		}
	}


}
