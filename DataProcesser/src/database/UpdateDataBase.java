package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class UpdateDataBase {
	//update abstracts which without stop words
	public void updateAbstractsWithoutSW(HashMap<String,String> abstractMap) {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		PreparedStatement psql = null;
		
		try {
			long startTime=System.currentTimeMillis();
			for(String getkey:abstractMap.keySet()) {
				psql = conn.prepareStatement("UPDATE defect_removestopwords set Abstract = ? where DefectID = ?");
				psql.setString(1, abstractMap.get(getkey));
				psql.setString(2, getkey);
				psql.executeUpdate();
			}
			psql.close();
			conn.close();
			long endTime=System.currentTimeMillis();
			float excTime=(float)(endTime-startTime)/1000;
			System.out.println("Update defect_removestopwords.Abstract is finished.The process need "+excTime+"s");  //37.125s
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				System.out.println("Update abstract without stop words is successed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

/*	public static void main(String[] args) {
		
	}*/

}
