package processer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Some Originator's and Owner's Name field is blank
   so through the relation between ID with Name of Originator, Owner to fill the blank field
*/
public class MatchIDName {
	
	public List<List<String>> processIdName(List<List<String>> defectList) {
		Map<String, String> idNameMap = new HashMap<String, String>();
		idNameMap = idNameMap(defectList,idNameMap);
		System.out.println(idNameMap.toString());
		defectList = recognizeName(defectList,idNameMap);
		return defectList;
	}
	
	
	public Map<String, String> idNameMap(List<List<String>> defectList,Map<String, String> idNameMap) {
		List<String> recordList = new ArrayList<String>();
		//Map<String, String> idNameMap = new HashMap<String, String>();
		
		for(int i=0; i < defectList.size(); i++) {
			recordList = defectList.get(i);
			if(!recordList.get(3).equals("") && !recordList.get(11).equals("")) {
				if(!idNameMap.containsKey(recordList.get(3))) {
					idNameMap.put(recordList.get(3), recordList.get(11));
				}
			}
			
			if(!recordList.get(4).equals("") && !recordList.get(12).equals("")) {
				if(!idNameMap.containsKey(recordList.get(4))) {
					idNameMap.put(recordList.get(4), recordList.get(12));
				}
			}
		}
		return idNameMap;
	}
	
	public List<List<String>> recognizeName(List<List<String>> defectList,Map<String, String> idNameMap) {
		List<String> recordList = new ArrayList<String>();
		for(int i=0; i < defectList.size(); i++) {
			recordList = defectList.get(i);

			//set Originator'Name and  Owner'Name when they are null
			if(recordList.get(11).equals("")) {
				String thirdField = recordList.get(3);
				if(idNameMap.containsKey(thirdField)) {
					recordList.set(11, idNameMap.get(thirdField));
				}
				if(thirdField.equals("zhangyj")) {
					recordList.set(11, "Zhang Yan Jie");
				}
				
			}
			//set Owner'Name when it is null
			if(recordList.get(12).equals("")) {
				String forthField = recordList.get(4);
				if(idNameMap.containsKey(forthField)) {
					recordList.set(12, idNameMap.get(forthField));
				}
				if(forthField.equals("zhangyj")) {
					recordList.set(11, "Zhang Yan Jie");
				}
			}
			defectList.set(i, recordList);
		}
		return defectList;
	}

}
