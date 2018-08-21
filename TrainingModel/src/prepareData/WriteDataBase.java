package prepareData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WriteDataBase {


	public void insertCleanRelease(List<List<String>> defectList,Connection conn) {
		PreparedStatement psql = null;
		
		try {
			long startTime=System.currentTimeMillis();
			for(int i=0; i < defectList.size();i++) {
				psql = null;
				psql = conn.prepareStatement("insert into defect_3(DefectID,Component,Releases,State," + 
						"Abstract,OriginatorName,OwnerName,Severty)"+"VALUES (?,?,?,?,?,?,?,?);");
				psql.setString(1, defectList.get(i).get(0));
				psql.setString(2, defectList.get(i).get(1));
				psql.setString(3, defectList.get(i).get(2));
				psql.setString(4, defectList.get(i).get(3));
				psql.setString(5, defectList.get(i).get(4));
				psql.setString(6, defectList.get(i).get(5));
				psql.setString(7, defectList.get(i).get(6));
				psql.setInt(8, Integer.parseInt(defectList.get(i).get(7)));
				psql.executeUpdate();
			}
			
			long endTime=System.currentTimeMillis();
			float excTime=(float)(endTime-startTime)/1000;
			System.out.println("Insert into database is finished.The process need "+excTime+"s");  //60.59s
			psql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				System.out.println("Write defects new information into table defect_3 is finished.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {

	}
	
}
