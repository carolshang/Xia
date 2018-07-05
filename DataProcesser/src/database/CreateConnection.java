package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateConnection {

	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://localhost:3306/analysisDefect";
	
	final static String DB_USER = "root";
	final static String DB_PASSWORD = "7u8i9o0p";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connect to the database ......");
			conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			stmt = conn.createStatement();
			
			String sql = "";
			sql = "";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {

                // Êä³öÊý¾Ý
                System.out.print("DefectId: " + rs.getString("DefectID"));
                System.out.print(", Component: " + rs.getString("Component"));
                System.out.print("\n");
			}
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

	}

}
