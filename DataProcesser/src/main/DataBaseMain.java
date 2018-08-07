package main;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstractProcess.RemoveStopWords;
import database.CreateConnection;
import database.UpdateDataBase;
import database.WriteIntoDataBase;
import fileRW.ReadNecessaryFromExcel;
import processer.ProcessMain;

public class DataBaseMain {

	public static void main(String[] args) {

			List<List<String>> defectList = new ArrayList<List<String>>();
			File file = new File("./files/CleanDefectInfoAll.xls");
			ReadNecessaryFromExcel obj = new ReadNecessaryFromExcel();
			defectList = obj.readExcel(file,defectList);
			
			ProcessMain pm = new ProcessMain();
			defectList = pm.processDataSuit(defectList);
			
			//write into mysql
			CreateConnection cc = new CreateConnection();
			Connection conn = cc.createConnection();
			WriteIntoDataBase writeIn = new WriteIntoDataBase();
			writeIn.Insert(defectList, conn);
			
			//remove stop words of abstract, then update abstract into database
			RemoveStopWords rsw = new RemoveStopWords();
			UpdateDataBase udb = new UpdateDataBase();
			HashMap<String,String> abstractMap = rsw.removeWords(); //5.297s
			udb.updateAbstractsWithoutSW(abstractMap);
	}

}
