package prepareData;

import java.util.List;
import java.util.Map;
/**
 * 1 get dataset, count the component match, if match count = 1, the record will mark as the component type
 *   count the words match, if the words count >=2 the record will mark as the words type
 * 2 update the dataset ,add type field in record
 * 3 count the data if all the data's type field exist and not null
 * 4 if type field does not exist, set the type field if match count = 1
 * 5 output result
 * @author carol
 *
 */
public class AbstractBTTType {

	// count the component match
	public List<List<String>> componentMatch(Map<String,List<String>> compMap,List<List<String>> defectList) {
		List<String> tempList = null;
		String dcomp = null; //component from data list
		String mcomp = null; //component from match file
		
		for(int i=0; i < defectList.size();i++) {
			tempList = defectList.get(i);
			for(String compkey:compMap.keySet()) {
				for(int j=0;j<compMap.get(compkey).size();j++) {
					dcomp = defectList.get(i).get(1);
					mcomp = compMap.get(compkey).get(j);
					if(dcomp.equals(mcomp)) {  //rely on the component to set different type
						if(tempList.size() > 8) {
							continue;
						}else {
							tempList.add(tempList.size(), compkey);
							defectList.set(i, tempList);
						}
					}
				}
			}
			if(tempList.size() <= 8 ) {
				tempList.add(tempList.size(), "Others");
				defectList.set(i, tempList);
			}
		}
		return defectList;
	}
	// count the words match
	public List<List<String>> wordsMatch(Map<String,List<String>> wordMap,List<List<String>> defectList) {
		List<String> tempList = null;
		String dabstr = null; //component from data list
		String[] abstrArr = null;
		String words = null; //component from match file
		
		for(int i=0; i < defectList.size();i++) {
			int count = 0;
			abstrArr = defectList.get(i).get(4).split(" ");
			tempList = defectList.get(i);
			for(String wordkey:wordMap.keySet()) {
				for(int j=0;j<wordMap.get(wordkey).size();j++) {
					words = wordMap.get(wordkey).get(j);
					for(int y=0;y<abstrArr.length;y++) {
						dabstr = abstrArr[y];
						if(dabstr.equals(words)) {  //rely on the component to set different type
							count++;
						}
					}
				}
				if(count > 1) { //match words more than 1, change the type to words type
					tempList.add(tempList.size(), wordkey);
					defectList.set(i, tempList);
				}
			}
			if(tempList.size() <= 8 ) {
				tempList.add(tempList.size(), "Others");
				defectList.set(i, tempList);
			}
		}
		
		return defectList;
	}
	
	public List<List<String>> setTypeLastTime(Map<String,List<String>> wordMap,List<List<String>> defectList){
		List<String> tempList = null;
		String dabstr = null; //component from data list
		String[] abstrArr = null;
		String words = null; //component from match file
		
		for(int i=0; i < defectList.size();i++) {
			if(defectList.get(i).size() <= 8) {
				abstrArr = defectList.get(i).get(4).split(" ");
				for(String wordkey:wordMap.keySet()) {
					for(int j=0;j<wordMap.get(wordkey).size();j++) {
						words = wordMap.get(wordkey).get(j);
						for(int y=0;y<abstrArr.length;y++) {
							dabstr = abstrArr[y];
							if(dabstr.equals(words)) {  //rely on the component to set different type
								tempList = defectList.get(i);
								tempList.add(tempList.size(), wordkey);
								defectList.set(i, tempList);
							}
						}
					}
				}
			}
		}
		return defectList;
	}
	
	public boolean isAllDataHaveType(List<List<String>> defectList) {
		boolean flag = true;
		for(int i=0;i<defectList.size();i++) {
			if(defectList.get(i).size() == 9) {
				flag = true;
				continue;
			}else {
				flag = false;
			}
		}
		System.out.println("*****************************************************************************************************************");
		System.out.println("All data is clustered or not ....."+flag);
		return flag;
	}
	
	public static void main(String[] args) {
		/*File output = new File("./output1.txt");
		File result = new File("./result1.txt");
		FileWriter fw = null;
		FileWriter fw1 = null;
			fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			fw1 = new FileWriter(result);
			BufferedWriter bw1 = new BufferedWriter(fw1);*/
	}

}
