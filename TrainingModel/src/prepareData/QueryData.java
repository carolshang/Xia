package prepareData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryData {

	public List<List<String>> queryAll() {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		Statement stmt = null;
		List<List<String>> defectList = new ArrayList<List<String>>();
		List<String> recordList = null;
		try {
			stmt = conn.createStatement();
			
			String sql = "SELECT * from defect_removestopwords";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				recordList = new ArrayList<String>();
				recordList.add(rs.getString("DefectID"));
				recordList.add(rs.getString("Component"));
				recordList.add(rs.getString("Releases"));
			//	recordList.add(rs.getString("OriginatorID"));
			//	recordList.add(rs.getString("OwnerID"));
				recordList.add(rs.getString("State"));
			//	recordList.add(rs.getDate("DateCreated"));
				recordList.add(rs.getString("Abstract"));
			//	recordList.add(rs.getDate("DateAssigned"));
			//	recordList.add(rs.getDate("DateClosedorCanceled"));
				recordList.add(rs.getString("Answer"));
				recordList.add(rs.getString("OriginatorName"));
				recordList.add(rs.getString("OwnerName"));
			//	recordList.add(rs.getDate("DateAcceptedorReturned"));
				recordList.add(rs.getInt("Severty")+"");
				
				defectList.add(i, recordList);
				i++;
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
		return defectList;
	}
	
	public List<List<String>> queryForClean(Connection conn) {
		Statement stmt = null;
		List<List<String>> defectList = new ArrayList<List<String>>();
		List<String> recordList = null;
		try {
			stmt = conn.createStatement();
			
			String sql = "SELECT * from defect_removestopwords";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				recordList = new ArrayList<String>();
				recordList.add(rs.getString("DefectID"));
				recordList.add(rs.getString("Component"));
				recordList.add(rs.getString("Releases"));
			//	recordList.add(rs.getString("OriginatorID"));
			//	recordList.add(rs.getString("OwnerID"));
				recordList.add(rs.getString("State"));
			//	recordList.add(rs.getDate("DateCreated"));
				recordList.add(rs.getString("Abstract"));
			//	recordList.add(rs.getDate("DateAssigned"));
			//	recordList.add(rs.getDate("DateClosedorCanceled"));
			//	recordList.add(rs.getString("Answer"));
				recordList.add(rs.getString("OriginatorName"));
				recordList.add(rs.getString("OwnerName"));
			//	recordList.add(rs.getDate("DateAcceptedorReturned"));
				recordList.add(rs.getInt("Severty")+"");
				
				defectList.add(i, recordList);
				i++;
			}
			rs.close();
			stmt.close();
		//	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				/*if(conn != null) {
					conn.close();
				}*/
				System.out.println("select * from defect_removestopwords is finished.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return defectList;
	}
	public List<List<String>> queryDataFromDefect3(Connection conn) {
		Statement stmt = null;
		List<List<String>> defectList = new ArrayList<List<String>>();
		List<String> recordList = null;
		try {
			stmt = conn.createStatement();
			
			String sql = "SELECT * from defect_3";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				recordList = new ArrayList<String>();
				recordList.add(rs.getString("DefectID"));
				recordList.add(rs.getString("Component"));
				recordList.add(rs.getString("Releases"));
				recordList.add(rs.getString("State"));
				recordList.add(rs.getString("Abstract"));
				recordList.add(rs.getString("OriginatorName"));
				recordList.add(rs.getString("OwnerName"));
				recordList.add(rs.getInt("Severty")+"");
				
				defectList.add(i, recordList);
				i++;
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
				System.out.println("select * from defect_3 is finished.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return defectList;
	}
	public List<List<String>> queryDefect3forClustering() {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		Statement stmt = null;
		List<List<String>> defectList = new ArrayList<List<String>>();
		List<String> recordList = null;
		try {
			stmt = conn.createStatement();
			
			String sql = "SELECT * from defect_3";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				recordList = new ArrayList<String>();
				recordList.add(rs.getString("DefectID"));
				recordList.add(rs.getString("Component"));
				recordList.add(rs.getString("Releases"));
			//	recordList.add(rs.getString("OriginatorID"));
			//	recordList.add(rs.getString("OwnerID"));
				recordList.add(rs.getString("State"));
			//	recordList.add(rs.getDate("DateCreated"));
			//	recordList.add(rs.getString("Abstract"));
			//	recordList.add(rs.getDate("DateAssigned"));
			//	recordList.add(rs.getDate("DateClosedorCanceled"));
			//	recordList.add(rs.getString("Answer"));
				recordList.add(rs.getString("OriginatorName"));
				recordList.add(rs.getString("OwnerName"));
			//	recordList.add(rs.getDate("DateAcceptedorReturned"));
				recordList.add(rs.getInt("Severty")+"");
				recordList.add(rs.getString("Type"));
				
				defectList.add(i, recordList);
				i++;
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
				System.out.println("select * from defect_3 for clustering is finished.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return defectList;
	}
	public static void main(String[] args) {

	}

}
