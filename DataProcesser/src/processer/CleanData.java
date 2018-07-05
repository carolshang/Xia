package processer;

import java.util.ArrayList;
import java.util.List;

public class CleanData {
	
	//the blank in Answer column set to program_defect
	public List<List<String>> fillAnswerBlank(List<List<String>> defectList){
		List<String> recordList = new ArrayList<String>();
		for(int i = 0;i < defectList.size(); i++) {
			recordList = defectList.get(i);
			//if Answer is null, set  Answer  = program_defect
			if(recordList.get(10).equals("")) {
				recordList.set(10, "program_defect");
			}
		}
		return defectList;
	}
	
	//TODO
/*	public List<List<String>> transferDateFormat(List<List<String>> defectList) {
		
		List<String> recordList = new ArrayList<String>();
		for(int i = 0;i < defectList.size(); i++) {
			recordList = defectList.get(i);
			//if Answer is null, set  Answer  = program_defect
			if(recordList.get(10).equals("")) {
				recordList.set(10, "program_defect");
			}
		}
		return defectList;
	}*/
	
	public static void main(String[] args) {
		

	}
	

}
