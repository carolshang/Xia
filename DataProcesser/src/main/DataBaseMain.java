package main;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import database.CreateConnection;
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
			Connection conn = CreateConnection.createConnection();
			WriteIntoDataBase writeIn = new WriteIntoDataBase();
			writeIn.Insert(defectList, conn);

	}

}
