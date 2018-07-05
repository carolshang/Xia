package processer;

import java.util.List;

public class ProcessMain {

	public List<List<String>> processDataSuit(List<List<String>> defectList) {
		
		//get the match map between originator id and name, owner id and name
		MatchIDName midn = new MatchIDName();
		defectList = midn.processIdName(defectList);
		
		CleanData cd = new CleanData();
		defectList =  cd.fillAnswerBlank(defectList);
		
		//modify date value from YYYY-MM-DD HH:MM:SS into YYYY-MM-DD
		
		return defectList;
	}
	
	/*public static void main(String[] args) {

	}
*/
}
