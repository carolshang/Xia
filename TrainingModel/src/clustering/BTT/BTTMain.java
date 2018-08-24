package clustering.BTT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTTMain {

	public static void main(String[] args) {
		File output = new File("./btttype_output2.txt");
		File result = new File("./btttype_result2.txt");
		FileWriter fw = null;
		FileWriter fw1 = null;
		int times = 0;
		long startTime=System.currentTimeMillis();
		try {
			fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			fw1 = new FileWriter(result);
			BufferedWriter bw1 = new BufferedWriter(fw1);

			//initial variables
			//TODO
			int k = 12; //cluster number
			List<List<String>> defectList = new ArrayList<List<String>>(); // init data set
			List<List<String>> centralList = new ArrayList<List<String>>(); //k central records
			Map<Integer,List<List<String>>> clusterMap = new HashMap<Integer,List<List<String>>>();  //record all cluster by group
			List<List<String>> allNewCentralList = new ArrayList<List<String>>();
			KModes_BTTType kmb = new KModes_BTTType();
			//TODO
			int recordSize = 0; // the attribute count  of each record
			//begin
			//get initial data set
			defectList = kmb.initialData(defectList);
			recordSize = defectList.get(0).size(); //every defect's attributes count, equals 8
			//get random central points
			centralList = kmb.getKCentralPoint(k,defectList);
			allNewCentralList = centralList;  //first time
			int matchSummary = 0;
			double unclusterRate = 0;
			//neither central points equals or coverRate is true, the loop will continue
			while(matchSummary != k*8/* || unclusterRate > 0.05*/) {  //when central is equal with last time and the coverRate is more than 0.95, the loop ending
				if(matchSummary == k*8) {
					allNewCentralList = kmb.getKCentralPoint(k,defectList);
				}
				defectList = kmb.clustering(k,defectList, allNewCentralList, bw1); //clustering defect list
				clusterMap = kmb.groupDataByCluster(k, recordSize, defectList, bw);
				centralList = allNewCentralList;
				allNewCentralList = kmb.getAllNewCentralPointByCluster(k, recordSize, defectList, centralList, clusterMap);
				matchSummary = kmb.compareNewOldCentralList(centralList, allNewCentralList, bw);
				times++; //record loop times
				unclusterRate = kmb.countLeavingDataByCluster(recordSize, defectList, bw);
				System.out.println(matchSummary+"-----"+unclusterRate);
			}
			boolean isAllClustered = true;
			isAllClustered = kmb.isAllDataIsCluster(recordSize, defectList, bw);
			//if the central points is stable, some data are still in neither cluster, add them into one cluster
			/*if(!isAllClustered) {
				defectList = kmb.finalCluster(k,recordSize, defectList);
				clusterMap = kmb.groupFinalCluster(k, recordSize, defectList, clusterMap);
			}*/
			while(!isAllClustered) {
				defectList = kmb.finalCluster(k,recordSize, defectList, bw);
				isAllClustered = kmb.isAllDataIsCluster(recordSize, defectList, bw);
			}
			clusterMap = kmb.groupFinalCluster(k, recordSize, defectList, clusterMap);
			//ending, out put the result
			kmb.outputResult(clusterMap, bw);
			
			bw.close();
			
			System.out.println("BTT type clustering result out put into files");
			System.out.println("Clustering times is "+times);
			long endTime=System.currentTimeMillis();
			float excTime=(float)(endTime-startTime)/1000;
			System.out.println("BTT type clustering is finished.The process need "+excTime+"s");  //3.056s  k=6 5.781s k=7 12.466s
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
