package fileRW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteDataIntoFile {

	File newfile = new File("./files/ProcessedDefectsInfo.txt");
	FileWriter fw = null;
	
	public void WriteProcessedIntoTXT(List<List<String>> defectList) {
		try {
			fw = new FileWriter(newfile);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i<defectList.size();i++) {
				bw.write(defectList.get(i).toString() + "\r\n");
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*	public static void main(String[] args) {

	}*/
}
