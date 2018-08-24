package prepareData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractClustering {

	public static void main(String[] args) {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		QueryData qd = new QueryData();
		List<List<String>> defectList = qd.queryDataFromDefect3(conn);
		AbstractClustering ac = new AbstractClustering();
		Map<String,List<String>> compMap = new HashMap<String,List<String>>(); //save component from BTT match
		Map<String,List<String>> wordMap = new HashMap<String,List<String>>(); //save words from BTT match
		compMap = ac.readComponentMatchFile(compMap);
		wordMap = ac.readWordsMatchFile(wordMap);
		//set record BTT type
		AbstractBTTType abt = new AbstractBTTType();
		boolean flag = true;
		//match
		defectList = abt.componentMatch(compMap, defectList);
		defectList = abt.wordsMatch(wordMap, defectList);
		flag = abt.isAllDataHaveType(defectList);
		while(!flag) {
			defectList = abt.setTypeLastTime(wordMap, defectList);
		}
		
		//update into database
		WriteDataBase wdb = new WriteDataBase();
		wdb.addTypeField(defectList);
		
		
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,List<String>> readComponentMatchFile(Map<String,List<String>> compMap) {
	//	Map<String,List<String>> compMap = new HashMap<String,List<String>>(); //save component from BTT match
		List<String> compList = null;
		File compfile = new File("./files/matchlist/componentBTTMatch.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(compfile));
			String temp = null;
			
			while((temp = br.readLine()) != null) {
				String[] compArr = temp.split(":"); 
				String type = compArr[0];
				String[] list= compArr[1].split(",");
				compList = new ArrayList<String>();
				for(int i=0;i< list.length;i++) {
					compList.add(list[i]);
				}
				compMap.put(type, compList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compMap;
	}
	
	public Map<String,List<String>> readWordsMatchFile(Map<String,List<String>> wordMap) {
		//Map<String,List<String>> wordMap = new HashMap<String,List<String>>(); //save words from BTT match
		List<String> wordList = null;
		File wordfile = new File("./files/matchlist/WordsBTTMatch.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(wordfile));
			String temp = null;
			while((temp = br.readLine()) != null) {
				String[] wordArr = temp.split(":"); 
				String type = wordArr[0];
				String[] list= wordArr[1].split(",");
				wordList = new ArrayList<String>();
				for(int i=0;i< list.length;i++) {
					wordList.add(list[i]);
				}
				wordMap.put(type, wordList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordMap;
	}

}
