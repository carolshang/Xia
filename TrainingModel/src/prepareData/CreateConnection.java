package prepareData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {

	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://localhost:3306/processeddefect?characterEncoding=utf8&useSSL=true";
	
	final static String DB_USER = "root";
	final static String DB_PASSWORD = "7u8i9o0p";
	
	public Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connect to the database ......");
			conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
/*	public static void main(String[] args) {
	}*/

}
