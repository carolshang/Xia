package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class WriteIntoDataBase {
	
	public void Insert(List<List<String>> defectList,Connection conn) {
	//	conn = CreateConnection.createConnection();
		PreparedStatement psql = null;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
		
		try {
			long startTime=System.currentTimeMillis();
			for(int i=0; i < defectList.size();i++) {
				psql = null;
				psql = conn.prepareStatement("insert into defect(DefectID,Component,Releases,OriginatorID,OwnerID,State,DateCreated," + 
						"Abstract,DateAssigned,DateClosedorCanceled,Answer," + 
						"OriginatorName,OwnerName,DateAcceptedorReturned,Severty)"+"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
				
				psql.setString(1, defectList.get(i).get(0));
				psql.setString(2, defectList.get(i).get(1));
				psql.setString(3, defectList.get(i).get(2));
				psql.setString(4, defectList.get(i).get(3));
				psql.setString(5, defectList.get(i).get(4));
				psql.setString(6, defectList.get(i).get(5));
				psql.setDate(7, new java.sql.Date(dateFormat.parse(defectList.get(i).get(6)).getTime()));
				psql.setString(8, defectList.get(i).get(7));
				psql.setDate(9, new java.sql.Date(dateFormat.parse(defectList.get(i).get(8)).getTime()));
				psql.setDate(10,new java.sql.Date(dateFormat.parse(defectList.get(i).get(9)).getTime()));
				psql.setString(11, defectList.get(i).get(10));
				psql.setString(12, defectList.get(i).get(11));
				psql.setString(13, defectList.get(i).get(12));
				psql.setDate(14,new java.sql.Date(dateFormat.parse(defectList.get(i).get(13)).getTime()));
				psql.setInt(15, Integer.parseInt(defectList.get(i).get(14)));
				psql.executeUpdate();
			}
			
			long endTime=System.currentTimeMillis();
			float excTime=(float)(endTime-startTime)/1000;
			System.out.println("Insert into database is finished.The process need "+excTime+"s");  //39.598s
			psql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				System.out.println("Write defects information into table defect is finished.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*public static void main(String[] args) {
		
	}*/

}
