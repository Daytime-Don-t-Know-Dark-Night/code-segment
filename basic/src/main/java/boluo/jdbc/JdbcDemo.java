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
		// 1.加载驱动
		Class.forName("com.mysql.jdbc.Driver");

		// 2.建立连接
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

			// 3.向数据库发送SQL
			res = stmt.executeQuery("select * from user");
			while (res.next()) {

				// 4.处理返回结果
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
			// 5.关闭连接
		}
	}

}
