package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import prepareData.QueryData;

public class KModes {

	public static void main(String[] args) {

		int k = 5; //classification number
		List<List<String>> defectList = new ArrayList<List<String>>();
		List<String> recordList = new ArrayList<String>();
		
		Random rand = new Random();
		QueryData qd = new QueryData();
		
		defectList = qd.queryAll();
		int dListSize = defectList.size();
		int rListSize = defectList.get(0).size(); //every defect's attributes count, equals 8
		//get k central point
		List<List<String>> centralList = new ArrayList<List<String>>();
		int cListSize = centralList.size();  //equals with k
		for(int i=0; i < k; i++) {
			int randTemp = rand.nextInt(dListSize);
			
			defectList.get(randTemp).set(rListSize+1, i+"");  //set the last column is the type k

			centralList.add(defectList.get(randTemp));
		}
		
		
		//clustering
		for(int i=0; i < dListSize;i++) {
			int count = 0;
			for(int j=0; j < cListSize; j++) {
				for(int n=1; n < 9; n++) { // from n=1, because of defectID is different with each other
					if(defectList.get(i).get(n).equals(centralList.get(j).get(n))) {
						count++;
					}
				}
				if(count > 3 && defectList.get(i).size() < 9) {
					defectList.get(i).set(rListSize+1, centralList.get(j).get(rListSize+1));
				}
			}
		}
		
		
		//update central point
		List<List<String>> kList = new ArrayList<List<String>>();
		for(int n=0;n<k;n++) {
			//each time find one cluster
			for(int j=0; j<dListSize;j++ ) {
				if(defectList.get(j).get(rListSize).equals(n+"")) {
					kList.add(defectList.get(j));
				}
			}
			//count the number of each record's attributes and record the frequent attribute as the central point attribute
			HashMap<String,Integer> attributeMap = new HashMap<String,Integer>();
			List<String> newCentralPointList = new ArrayList<String>();
			
			for(int i=1; i< rListSize+1;i++) {  //size is equals records size + 1(type column) , from i=1,because of the defectID is different with each other
				for(int m=0; m<kList.size();m++) {   //iterator the one type records
					String key = kList.get(m).get(i);
					if(attributeMap.containsKey(key)) {
						attributeMap.put(key,attributeMap.get(key)+1);
					}else {
						attributeMap.put(key,1);
					}
				}
				Object[] obj = attributeMap.values().toArray();
				Arrays.sort(obj);
				String attributeFre = null;  //get the frequent attribute
				for(String getkey:attributeMap.keySet()) {
					if(attributeMap.get(getkey).equals(obj[obj.length-1])) {
						attributeFre = getkey;
					}
				}
				newCentralPointList.add(i, attributeFre);
				
			}
			//compare the new central point with the last time central point
			String clusterType = newCentralPointList.get(newCentralPointList.size()-1);
			for(int t=0; t< kList.size()+1;t++) {
					String tempType = kList.get(t).get(kList.get(0).size()-1);
					if(tempType.equals(clusterType)) {
						//compare the two records match rate, if the rate is higher ,don't update the central point, else update the central point
					}
				
				
				
			}
			
			
			kList = null;  //next cluster, clean list
		}
		//output the result
		
		

	}

}
