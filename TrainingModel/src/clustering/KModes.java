package clustering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		/*String[][] a = {{"book","red","james","58"},
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
		}*/
		QueryData qd = new QueryData();
		defectList = qd.queryAll();
		return defectList;
	}
	//get k central point
	public List<List<String>> getKCentralPoint(int k,List<List<String>> defectList,List<List<String>> centralList) {
		//randtemp must different,so need to add the limitation
		for(int i=0; i < k; i++) {
			int randTemp = rand.nextInt(defectList.size());
			//defectList.get(randTemp).add(4, i+"");  //set the last column is the type k
			//TODO
			defectList.get(randTemp).add(9, i+"");
			centralList.add(defectList.get(randTemp));
		}
		return centralList;
	}
	//clustering
	public List<List<String>> clustering(int k,List<List<String>> defectList,List<List<String>> centralList,BufferedWriter bw1) {
		List<String> tempList = null;
		for(int i=0; i < defectList.size();i++) {
			for(int j=0; j < k; j++) {
				int count = 0;
				//TODO N<9
				//for(int n=0; n < 4; n++) { // for test data
				for(int n=1; n < 9; n++) { // from n=1, because of defectID is different with each other
					if(defectList.get(i).get(n).equals(centralList.get(j).get(n))) {
						count++;
					}
				}
				tempList = new ArrayList<String>();
				//TODO count > 3 && defectList.get(i).size() < 9
				//if(count >= 2 && defectList.get(i).size() < 6) { // for test data
				if(count > 2 /*&& defectList.get(i).size() < 11*/) {
					tempList = defectList.get(i);
					if(tempList.size() == 10) {
						tempList.set(9, centralList.get(j).get(9));
					}else {
						tempList.add(9, centralList.get(j).get(9));
					}
					defectList.set(i, tempList);
				}
				try {
					bw1.write(defectList.get(i).toString()+"\r\n");
					bw1.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	System.out.println(defectList.get(i));
			}
		}
		return defectList;
	}
	//put data into different set by cluster
	public Map<Integer,List<List<String>>> groupDataByCluster(int k,int recordSize,List<List<String>> defectList,BufferedWriter bw) {
		Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>(); //cluster map which is the clustering result
		List<List<String>> kList = null;
		for(int n=0;n<k;n++) {
			kList = new ArrayList<List<String>>();
			//each time find one cluster
			for(int j=0; j<defectList.size();j++ ) {
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
					//System.out.println("clusterMap-------"+kList.toString());
				}
			}
			try {
				bw.write("*****************************************************************************************************************\r\n");
				bw.write("clusterMap---"+n+"---"+kList.size()+"\r\n"+"       "+kList.toString()+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("clusterMap---"+n+"---"+kList.size()+"---"+kList.toString());
		}
		return clusterMap;
	}
	
	public Map<Integer,List<List<String>>> groupFinalCluster(int k,int recordSize,List<List<String>> defectList,Map<Integer,List<List<String>>> clusterMap) {
		//Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>();
		List<List<String>> kList = new ArrayList<List<String>>();
		for(int i=0; i< defectList.size();i++) {
			//for(Integer key:clusterMap.keySet()) {
				//if(!.equals(Integer.toString(key))) {
				if(!clusterMap.containsKey(Integer.parseInt(defectList.get(i).get(recordSize)))){
					kList.add(defectList.get(i));
				}
				//}
			//}
		}
		clusterMap.put(k, kList);
		return clusterMap;
	}
	
	public List<String> getNewCentralPoint(int recordSize,List<List<String>> kList) {
		//count the number of each record's attributes and record the frequent attribute as the central point attribute
		HashMap<String,Integer> attributeMap = null;
		List<String> newCentralPointList = new ArrayList<String>();
		
		for(int i=0; i< recordSize+1;i++) {  //size is equals records size + 1(type column) , the defectID will be ignored during cluster
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
	public List<List<String>> getAllNewCentralPointByCluster(int k,int recordSize,List<List<String>> defectList,List<List<String>> centralList,Map<Integer,List<List<String>>> clusterMap) {
		List<List<String>> allNewCentralList = new ArrayList<List<String>>();
		List<String> newCentralPointList = new ArrayList<String>();
		List<List<String>> kList = null;
		for(int n=0;n<k;n++) {
			kList = new ArrayList<List<String>>();
			//each time find one cluster
			for(int key:clusterMap.keySet()) {
				if(key == n) {
					kList = clusterMap.get(key);
				}else {
					continue;
				}
			}
			newCentralPointList = getNewCentralPoint(recordSize,kList);
			
			allNewCentralList.add(n,newCentralPointList);
		}
		return allNewCentralList;
	}
	//compare newCentralList and oldCentralList
	public int compareNewOldCentralList(List<List<String>> centralList,List<List<String>> allNewCentralList,BufferedWriter bw) {
		int matchCount=0,matchSummary=0;
		for(int j=0;j<allNewCentralList.size();j++) {
			for(int i=0;i<centralList.size();i++) {
				matchCount = compareNewOldCentralPoint(centralList.get(i),allNewCentralList.get(j), bw);
				//if new and old have more than 5 field is equal, than the central point is stable
				//if(matchCount < 3) {
				if(matchCount < 5) {
					continue;
				}else {
					break;
				}
			}
			//matchSummary is the all list count summary, if matchSummary > k * 3, central point doesn't need update
			//if only one or two cluster matchCount > 3, so the matchSummary will less than k*3, the central point must update for other unstable cluster
			matchSummary += matchCount;
		}
		return matchSummary;
	}

	public int compareNewOldCentralPoint(List<String> oldList,List<String> newList,BufferedWriter bw) {
		int matchCount = 0;
		//compare the new central point with the last time central point
		try {
			String oldCluster = oldList.get(oldList.size()-1);
			String newCluster = newList.get(newList.size()-1);
			
			if(oldCluster.equals(newCluster)) {
				bw.write("*****************************************************************************************************************\r\n");
				bw.write("the last central point :"+ oldList.toString()+"\r\n");
				bw.write("new central point :"+ newList.toString()+"\r\n");
				//System.out.println("the last central point :"+ oldList.toString());
				//System.out.println("new central point :"+ newList.toString());
				matchCount = 0;
				//TODO
				//list loop all begin from 1 to list.size(); because the first is defectID
				//compare the two records match rate, if the rate is higher ,don't update the central point, else update the central point
			//	for(int j=0;j<oldList.size();j++) { //the last central point
				for(int j=1;j<oldList.size()-1;j++) { //the last central point
					if(oldList.get(j).equals(newList.get(j))) {
						matchCount++;
					}
				}
				bw.write("new and last central point comapre :"+matchCount+"\r\n");
				bw.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				//System.out.println("new and last central point comapre :"+matchCount);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return matchCount;
	}
	
	public boolean isAllDataIsCluster(int recordSize, List<List<String>> defectList,BufferedWriter bw) {
		boolean flag = true;
		for(int i=0;i<defectList.size();i++) {
			if(defectList.get(i).size() == recordSize+1) {
				continue;
			}else {
				flag = false;
			}
		}
		try {
			bw.write("*****************************************************************************************************************\r\n");
			bw.write("All data is clustered or not ....."+flag+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("All data is clustered or not ....."+flag);
		return flag;
	}
	
	public List<List<String>> finalCluster(int k,int recordSize,List<List<String>> defectList){
		List<String> tempList = new ArrayList<String>();
		for(int i=0;i<defectList.size();i++) {
			if(defectList.get(i).size() == recordSize+1) {
				continue;
			}else {
				tempList = defectList.get(i);
				tempList.add(recordSize, Integer.toString(k));
				defectList.set(i, tempList);
			}
		}
		return defectList;
	}
	
	public void outputResult(Map<Integer,List<List<String>>> clusterMap,BufferedWriter bw) {
		try {
			bw.write("*****************************************************************************************************************"+"\r\n");
			bw.write("Cluster results showing ......"+"\r\n");
			//System.out.println("Cluster results showing ......");
			for(Integer getkey:clusterMap.keySet()) {
				for(int i=0; i < clusterMap.get(getkey).size();i++) {
					bw.write(clusterMap.get(getkey).get(i).toString()+"\r\n");
					System.out.println(clusterMap.get(getkey).get(i).toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		File output = new File("./output1.txt");
		File result = new File("./result1.txt");
		FileWriter fw = null;
		FileWriter fw1 = null;
		try {
			fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			fw1 = new FileWriter(result);
			BufferedWriter bw1 = new BufferedWriter(fw1);
	//		bw.write(defectList.get(i).toString() + "\r\n");

			//initial variables
			//TODO
			int k = 6; //cluster number
			List<List<String>> defectList = new ArrayList<List<String>>(); // init data set
			List<List<String>> centralList = new ArrayList<List<String>>(); //k central records
			Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>();  //record all cluster by group
			List<List<String>> allNewCentralList = new ArrayList<List<String>>();
			KModes km = new KModes();
			//TODO
		//	int defectsize = 0; //the count of all records
			int recordSize = 0; // the attribute count  of each record
			//begin
			//get initial data set
			defectList = km.initialData(defectList);
		//	defectsize = defectList.size();
			recordSize = defectList.get(0).size(); //every defect's attributes count, equals 8
			//get random central points
			centralList = km.getKCentralPoint(k,defectList,centralList);
			allNewCentralList = centralList;  //first time
			int matchSummary = 0;
			boolean isAllClustered = true;
			while(matchSummary < k*5) {
				//defectList = km.initialData(defectList); //initial defectList every time
				defectList = km.clustering(k,defectList, allNewCentralList, bw1); //clustering defect list
				//TODO
				//first time, output defectList
				/*if(matchSummary == 0) {
					System.out.println("First time the cluster results .............");
					for(int i=0; i<defectList.size();i++) {
						System.out.println(defectList.get(i).toString());
					}	
				}*/
				
				clusterMap = km.groupDataByCluster(k, recordSize, defectList, bw);
				centralList = allNewCentralList;
				allNewCentralList = km.getAllNewCentralPointByCluster(k, recordSize, defectList, centralList, clusterMap);
				matchSummary = km.compareNewOldCentralList(centralList, allNewCentralList, bw);
			}
			isAllClustered = km.isAllDataIsCluster(recordSize, defectList, bw);
			//if the central points is stable, some data are still in neither cluster, add them into one cluster
			if(!isAllClustered) {
				defectList = km.finalCluster(k,recordSize, defectList);
				clusterMap = km.groupFinalCluster(k, recordSize, defectList, clusterMap);
			}
			//ending, out put the result
			km.outputResult(clusterMap, bw);
			
			bw.close();
			System.out.println("out put into files");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
