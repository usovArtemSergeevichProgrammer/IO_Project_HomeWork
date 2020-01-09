package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtils {

	public static String DB_NAME = null;
	static {
		DB_NAME = AppSettings.getInstance().getValue("db_name");
	}

	public static void switchDBName(String key) {
		DB_NAME = AppSettings.getInstance().getValue(key);
	}

	static String DB_USER = "root";
	static String DB_USER_PSW = "";
	static String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

	public static Connection createConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_USER_PSW);
			if (conn == null) {
				System.out.println(String.format("Can't connect to %s", DB_URL));
			} else {
				System.out.println(String.format("Opening Connection ID: %s", conn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void release(Connection conn, PreparedStatement pstmt) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				System.out.println(String.format("Closing Connection ID: %s", conn));
			}
			if (pstmt != null && !pstmt.isClosed()) {
				pstmt.close();
				System.out.println(String.format("Closing PreparedStatement ID: %s", pstmt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
