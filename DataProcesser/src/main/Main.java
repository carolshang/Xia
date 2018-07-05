package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fileRW.ReadNecessaryFromExcel;
import fileRW.WriteDataIntoFile;
import processer.ProcessMain;

//The excute of the Project
public class Main {

	public static void main(String[] args) {
	//	List<String> recordList = new ArrayList<String>();
		List<List<String>> defectList = new ArrayList<List<String>>();
		//read file and get the all defects
	//	File file = new File("./files/CleanDefectsInfo.xls");
		File file = new File("./files/CleanDefectInfoAll.xls");
		ReadNecessaryFromExcel obj = new ReadNecessaryFromExcel();
		defectList = obj.readExcel(file,defectList);
		
		ProcessMain pm = new ProcessMain();
		defectList = pm.processDataSuit(defectList);
		//processed data and write into txt file
		WriteDataIntoFile wdif = new WriteDataIntoFile();
		wdif.WriteProcessedIntoTXT(defectList);
	}

}
