package abstractProcess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import database.CreateConnection;

public class GetStopWords {
	
	public HashMap<String,Integer> SearchAllAbstract(Connection conn) {
		Statement stmt = null;
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		try {
			stmt = conn.createStatement();
			
			String sql = "SELECT * from defect";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String ab[] = rs.getString("Abstract").split(" ");
				for(int i=0; i < ab.length; i++) {
					if(ab[i].equals("") || ab[i].equals(" ")) {
						break;
					}
					if(hm.containsKey(ab[i].trim())) {
						hm.replace(ab[i].trim(), hm.get(ab[i].trim())+1);
					}else {
						hm.put(ab[i].trim(), 1);
					}
				}
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hm;
	
	}
	
	public void writeIntoStopWordsFile(HashMap<String,Integer> hm) {

		//write into txt file
		File newfile = new File("./files/AbstractWordsCount.txt");
		FileWriter fw = null;
		try {
			fw = new FileWriter(newfile);
			BufferedWriter bw = new BufferedWriter(fw);
			// output the large amount of words
	        System.out.println("Abstract - the words count begin........");
	        System.out.println("Output words count more than 30........");
	        for(String getkey:hm.keySet()) {
	        	if(hm.get(getkey) > 30) {
	        		bw.write(getkey+"---"+hm.get(getkey)+"\n");
	        		//System.out.print(getkey+":"+hm.get(getkey));
	        		bw.flush();
	        	
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Write into AbstractWordsCount.txt is finished.");
	}

	/*public static void main(String[] args) {
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		hm = SearchAllAbstract(conn);
		writeIntoStopWordsFile(hm);
	}*/

}
