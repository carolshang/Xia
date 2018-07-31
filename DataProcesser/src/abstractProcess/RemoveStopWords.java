package abstractProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.QueryData;

public class RemoveStopWords {
	
	public List<String> getStopWords() {
		List<String> stopwordsList = new ArrayList<String>();
		File swfile = new File("./files/StopWords.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(swfile));
			String temp = null;
			while((temp = br.readLine()) != null) {
				stopwordsList.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Get stop words............");
		return stopwordsList;
	}
	public String[] replaceWords(String abstr[],List<String> stopwordsList) {
		for(int i=0; i < stopwordsList.size(); i++) {
			for(int j=0;j < abstr.length; j++) {
				if(abstr[j].equals(stopwordsList.get(i))) {
					abstr[j] = "";
				}else {
					continue;
				}
			}
		}
		return abstr;
	}
	public HashMap<String,String> removeWords() {
		QueryData qd = new QueryData();
		HashMap<String,String> abstractMap = qd.SearchAllAbstracts();
		List<String> stopwordsList = getStopWords();
		//int count = 0;
		for(String getKey:abstractMap.keySet()) {
			String abstr[] = abstractMap.get(getKey).split(" ");
			//count++;
			abstr = replaceWords(abstr,stopwordsList);
			String abstrSimple = "";
			for(int n=0;n < abstr.length; n++) {
				if(abstr[n].equals("")) {
					continue;
				}else {
					if(n != abstr.length-1) {
						abstrSimple = abstrSimple + abstr[n]+" ";
					}else {
						abstrSimple = abstrSimple + abstr[n];
					}
				}
			}
			//System.out.println(abstrSimple);
			abstractMap.put(getKey, abstrSimple);
		}
		System.out.println("Remove stop words............");
		//System.out.println(count);
		return abstractMap;
	}
/*	public static void main(String[] args) {
		RemoveStopWords rsw = new RemoveStopWords();
		HashMap<String,String> abstractMap = rsw.removeWords();
	}*/

}
