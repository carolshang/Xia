package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import prepareData.QueryData;
/**
 * 1 random k central point , add into central list
 * 2 get dataset, count the attribute match, if match count >= 3, the record will mark as the k cluster
 * 3 add the dataset group by cluster into a map or set or list
 * 4 count the data in map(set/list), according to the frequent to get new central points
 * 5 clustering all initial data with new central points,  until the clusters doesn't change,count the number of data which cluster changed
   if the changed data divide all data number less than 5%, the clustering process is over
 * 6 output all data group by cluster
 * @author carol
 *
 */
public class KModes {
	Random rand = new Random();
	
	
	public List<List<String>> initialData(List<List<String>> defectList) {
		//testdata
		//TODO
		String[][] a = {{"book","red","james","58"},
						{"vedio","blue","jane","68"},
						{"book","blue","tom","58"},
						{"vedio","green","jane","70"},
						{"book","red","jane","48"}};
		for(int i=0;i<a.length;i++) {
			List<String> rList = new ArrayList<String>();
			for(int j=0;j<a[i].length;j++) {
				rList.add(a[i][j]);
			}
			defectList.add(rList);
		}
//		QueryData qd = new QueryData();
//		defectList = qd.queryAll();
		return defectList;
	}
	//get k central point
	public List<List<String>> getKCentralPoint(int k,int defectsize,List<List<String>> defectList,List<List<String>> centralList) {
		//TODO, randtemp must different,so need to add the limitation
		for(int i=0; i < k; i++) {
			int randTemp = rand.nextInt(defectsize);
			defectList.get(randTemp).add(4, i+"");  //set the last column is the type k
			centralList.add(defectList.get(randTemp));
		}
		return centralList;
	}
	//clustering
	public List<List<String>> clustering(int k,int defectsize,List<List<String>> defectList,List<List<String>> centralList) {
		//TODO
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
		return defectList;
	}
	//put data into different set by cluster
	public Map<Integer,List<List<String>>> groupDataByCluster(int k,int defectsize,int recordSize,List<List<String>> defectList) {
		Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>(); //cluster map which is the clustering result
		List<List<String>> kList = null;
		for(int n=0;n<k;n++) {
			kList = new ArrayList<List<String>>();
			//each time find one cluster
			for(int j=0; j<defectsize;j++ ) {
				if(defectList.get(j).size() == recordSize+1 && defectList.get(j).get(recordSize).equals(n+"")) {
					//kList.add(defectList.get(j));
					if(clusterMap.containsKey(n)) {
						kList = clusterMap.get(n);
						kList.add(defectList.get(j));
						clusterMap.replace(n, kList);
					}else {
						kList.add(defectList.get(j));
						clusterMap.put(n, kList);
					}
					System.out.println("clusterMap-------"+kList.toString());
				}
			}
		}
		return clusterMap;
	}
	
	public List<String> getNewCentralPoint(int recordSize,List<List<String>> kList) {
		//count the number of each record's attributes and record the frequent attribute as the central point attribute
		HashMap<String,Integer> attributeMap = null;
		List<String> newCentralPointList = new ArrayList<String>();
		//TODO
		for(int i=0; i< recordSize+1;i++) {  //size is equals records size + 1(type column) , from i=1,because of the defectID is different with each other
			attributeMap = new HashMap<String,Integer>();
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
		return newCentralPointList;
	}
	//update central point
	public List<List<String>> getAllNewCentralPointByCluster(int k,int defectsize,int recordSize,List<List<String>> defectList,List<List<String>> centralList,Map<Integer,List<List<String>>> clusterMap) {
		List<List<String>> allNewCentralList = new ArrayList<List<String>>();
		List<String> newCentralPointList = new ArrayList<String>();
		List<List<String>> kList = null;
		for(int n=0;n<k;n++) {
			kList = new ArrayList<List<String>>();
			//each time find one cluster
			/*for(int j=0; j<defectsize;j++ ) {
				if(defectList.get(j).size() == recordSize+1 && defectList.get(j).get(recordSize).equals(n+"")) {
					kList.add(defectList.get(j));
				}
			}*/
			for(int key:clusterMap.keySet()) {
				if(key == n) {
					kList = clusterMap.get(key);
				}else {
					continue;
				}
			}
			newCentralPointList = getNewCentralPoint(recordSize,kList);
			
			allNewCentralList.add(newCentralPointList);
		}
		return allNewCentralList;
	}
	//compare newCentralList and oldCentralList
	public int compareNewOldCentralList(List<List<String>> centralList,List<List<String>> allNewCentralList) {
		int matchCount=0,matchSummary=0;
		for(int i=0;i<centralList.size();i++) {
			for(int j=0;j<allNewCentralList.size();j++) {
				matchCount = compareNewOldCentralPoint(centralList.get(i),allNewCentralList.get(j));
				//if new and old have more than 3 field is equal, than the central point is stable
				if(matchCount > 3) {
					break;
				}else {
					continue;
				}
			}
			//matchSummary is the all list count summary, if matchSummary > k * 3, central point doesn't need update
			//if only one or two cluster matchCount > 3, so the matchSummary will less than k*3, the central point must update for other unstable cluster
			matchSummary += matchCount;
		}
		return matchSummary;
	}

	public int compareNewOldCentralPoint(List<String> oldList,List<String> newList) {
		int matchCount = 0;
		//compare the new central point with the last time central point
		System.out.println("the last central point :"+ oldList.toString());
		System.out.println("new central point :"+ newList.toString());
		String oldCluster = oldList.get(oldList.size()-1);
		String newCluster = newList.get(newList.size()-1);
		if(oldCluster.equals(newCluster)) {
			matchCount = 0;
			//compare the two records match rate, if the rate is higher ,don't update the central point, else update the central point
			for(int j=0;j<oldList.size();j++) { //the last central point
				if(oldList.get(j).equals(newList.get(j))) {
					matchCount++;
				}
			}
		}
		System.out.println("new and last central point comapre :"+matchCount);
		return matchCount;
	}
	
	public void outputResult(Map<Integer,List<List<String>>> clusterMap) {
		for(Integer getkey:clusterMap.keySet()) {
			for(int i=0; i < clusterMap.get(getkey).size();i++) {
				System.out.println(clusterMap.get(getkey).get(i).toString());
			}
		}
	}
	public static void main(String[] args) {
		//initial variables
		//TODO
		int k = 2; //cluster number
		List<List<String>> defectList = new ArrayList<List<String>>(); // init data set
		List<List<String>> centralList = new ArrayList<List<String>>(); //k central records
		Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>();
		List<List<String>> allNewCentralList = new ArrayList<List<String>>();
		KModes km = new KModes();
		//TODO
		int defectsize = 0; //the count of all records
		int recordSize = 0; // the attribute count  of each record
		//begin
		//get initial data set
		defectList = km.initialData(defectList);
		defectsize = defectList.size();
		recordSize = defectList.get(0).size(); //every defect's attributes count, equals 8
		centralList = km.getKCentralPoint(k,defectsize,defectList,centralList);
		allNewCentralList = centralList;  //first time
		int matchSummary = 0;
		while(matchSummary <= k*3) {
			defectList = km.initialData(defectList);
			defectList = km.clustering(k, defectsize, defectList, allNewCentralList);
			//TODO
			//first time, output defectList
			for(int i=0; i<defectList.size();i++) {
				System.out.println(defectList.get(i).toString());
			}
			
			clusterMap = km.groupDataByCluster(k, defectsize, recordSize, defectList);
			allNewCentralList = km.getAllNewCentralPointByCluster(k, defectsize, recordSize, defectList, centralList, clusterMap);
			matchSummary = km.compareNewOldCentralList(centralList, allNewCentralList);
		}
		//ending, out put the result
		km.outputResult(clusterMap);
	}

}
