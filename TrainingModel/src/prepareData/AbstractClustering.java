package prepareData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AbstractClustering {

	public static void main(String[] args) {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		QueryData qd = new QueryData();
		List<List<String>> defectList = qd.queryDataFromDefect3(conn);
		File datafile = new File("./defect_3_data.txt");
		FileWriter fw = null;
		try {
			fw = new FileWriter(datafile);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i<defectList.size();i++) {
				bw.write(defectList.get(i).toString() + "\r\n");
				bw.flush();
			}
			bw.close();
			/*BufferedReader br = new BufferedReader(new FileReader(datafile));
			String temp = null;
		//	br.readLine();
			while((temp = br.readLine()) != null) {
				
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
