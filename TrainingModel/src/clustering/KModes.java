package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import prepareData.QueryData;

public class KModes {

	public static void main(String[] args) {
		//1 random k central point , add into central list
		//2 get dataset, count the attribute match, if match count >= 3, the record will mark as the k cluster
		//3 add the dataset group by cluster into a map or set or list
		//4 count the data in map(set/list), according to the frequent to get new central points
		//5 clustering all initial data with new central points,  until the clusters doesn't change,count the number of data which cluster changed
		//  if the changed data divide all data number less than 5%, the clustering process is over
		//6 output all data group by cluster
		
		//testdata
		//TODO
		String[][] a = {{"book","red","james","58"},
						{"vedio","blue","jane","68"},
						{"book","blue","tom","58"},
						{"vedio","green","jane","70"},
						{"book","red","jane","48"}};
		
		
		
		//initial variables
		//TODO
		int k = 2; //cluster number
		
		List<List<String>> defectList = new ArrayList<List<String>>(); // init data set
		//TODO
		for(int i=0;i<a.length;i++) {
			List<String> rList = new ArrayList<String>();
			for(int j=0;j<a[i].length;j++) {
				rList.add(a[i][j]);
			}
			defectList.add(rList);
		}
		
		
		
		int defectsize = 0; //the count of all records
		
		int recordSize = 0; // the attribute count  of each record
		
		List<List<String>> centralList = new ArrayList<List<String>>(); //k central records
		
		Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>(); //cluster map which is the clustering result
		
		//begin
		//get initial data set
		Random rand = new Random();
//		QueryData qd = new QueryData();
//		defectList = qd.queryAll();
		defectsize = defectList.size();
		recordSize = defectList.get(0).size(); //every defect's attributes count, equals 8
		
		//get k central point
		//TODO, randtemp must different,so need to add the limitation
		for(int i=0; i < k; i++) {
			int randTemp = rand.nextInt(defectsize);
			defectList.get(randTemp).add(4, i+"");  //set the last column is the type k
			centralList.add(defectList.get(randTemp));
		}
		
		//TODO
		//clustering
		for(int i=0; i < defectsize;i++) {
			for(int j=0; j < k; j++) {
				int count = 0;
				//TODO N<8
				for(int n=0; n < 4; n++) { // from n=1, because of defectID is different with each other
					if(defectList.get(i).get(n).equals(centralList.get(j).get(n))) {
						count++;
					}
				}
				//TODO count > 3 && defectList.get(i).size() < 9
				if(count >= 2 && defectList.get(i).size() < 6) {
					if(defectList.get(i).size() == 5) {
						defectList.get(i).set(4, centralList.get(j).get(4));
					}else {
						defectList.get(i).add(4, centralList.get(j).get(4));
					}
				}
			}
		}
		
		//TODO
		//first time, output defectList
		for(int i=0; i<defectList.size();i++) {
			System.out.println(defectList.get(i).toString());
		}
		
		
		//update central point
		List<List<String>> kList = new ArrayList<List<String>>();
		for(int n=0;n<k;n++) {
			//each time find one cluster
			for(int j=0; j<defectsize;j++ ) {
				if(defectList.get(j).size() == recordSize+1 && defectList.get(j).get(recordSize).equals(n+"")) {
					kList.add(defectList.get(j));
				}
			}
			//count the number of each record's attributes and record the frequent attribute as the central point attribute
			HashMap<String,Integer> attributeMap = null;
			List<String> newCentralPointList = new ArrayList<String>();
			//TODO
			for(int i=0; i< recordSize+1;i++) {  //size is equals records size + 1(type column) , from i=1,because of the defectID is different with each other
				for(int m=0; m<kList.size();m++) {   //iterator the one type records
					attributeMap = new HashMap<String,Integer>();
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
		for(Integer getkey:clusterMap.keySet()) {
			
		}
		

		
		
		
		
	}

}
