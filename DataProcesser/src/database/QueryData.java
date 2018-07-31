package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryData {

	public HashMap<String,String> SearchAllAbstracts() {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		Statement stmt = null;
		HashMap<String,String> abstractMap = new HashMap<String,String>();
		try {
			stmt = conn.createStatement();
			
			String sql = "SELECT * from defect";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				abstractMap.put(rs.getString("DefectID"), rs.getString("Abstract"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
				System.out.println("select * from defect is finished.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return abstractMap;
	}
	public static void main(String[] args) {

	}

}
