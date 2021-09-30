package boluo.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcDemo {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(JdbcDemo.class);

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boluo", "root", "root");
		return conn;
	}

	public static void main(String[] args) {
		JdbcDemo jdbc = new JdbcDemo();
		Connection conn = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			conn = jdbc.getConnection();
			stmt = conn.createStatement();
			res = stmt.executeQuery("select * from user");
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
		}
	}

}
