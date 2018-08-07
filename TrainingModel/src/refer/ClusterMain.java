package refer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

//The main class to build clustering model and show result
public class ClusterMain {

	public static void main(String[] args) {
		int k = 5;
		int count = 0;
		
		Data data = new Data();
		List<List<String>> centerPoints = new ArrayList<List<String>>();
		List<List<String>> defectList = data.readTrainingData();
		
		
	//	Map<Integer, Map<String, Double>> meansMap = new TreeMap<Integer, Map<String, Double>>();//����K���������ĵ�����
	//	System.out.println("���ξ���ĳ�ʼ���Ӧ���ļ�Ϊ��");
	//	Set<Map.Entry<String, Map<String,Double>>> allTestSampleMapSet = allTestSampleMap.entrySet();
		for(int i=0 ; i<defectList.size();i++){
			//Map.Entry<String, Map<String,Double>> me = it.next();
			if(count == i * defectList.size() / k){
				centerPoints.add(defectList.get(i));
			//	meansMap.put(i, me.getValue());
				System.out.println(i + "------- " + defectList.get(i));
			}
			if(centerPoints.size() > k) {
				break;
			}
			count++;
		}

	}

}
