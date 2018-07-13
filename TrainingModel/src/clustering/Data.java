package clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Data {
	
	public List<List<String>> readTrainingData() {
		File trainfile = new File("./files/data/TrainingData.txt");
		BufferedReader br = null;
		List<String> recordList = null;
		List<List<String>> defectList = new ArrayList<List<String>>();
		if(trainfile.exists()){
			if(trainfile.isFile()){
				try {
					br = new BufferedReader(new FileReader(trainfile));
					String temp = null;
					while((temp = br.readLine()) != null){
						String[] eachRecord = temp.split(",");
						recordList = new ArrayList<String>();
						for(String field : eachRecord){
							recordList.add(field.trim());
						}
				//		System.out.println(recordList.toString());
						defectList.add(recordList);
					}
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("TrainingData.txt isn't a file.");
			}
		}else {
			System.out.println("TrainingData.txt isn't exist.");
		}	
		
		return defectList;
	}
	
	public List<List<String>> readTestData() {
		File trainfile = new File("./files/data/TestData.txt");
		BufferedReader br = null;
		List<String> recordList = null;
		List<List<String>> defectList = new ArrayList<List<String>>();
		if(trainfile.exists()){
			if(trainfile.isFile()){
				try {
					br = new BufferedReader(new FileReader(trainfile));
					String temp = null;
					while((temp = br.readLine()) != null){
						String[] eachRecord = temp.split(",");
						recordList = new ArrayList<String>();
						for(String field : eachRecord){
							recordList.add(field.trim());
						}
						System.out.println(recordList.toString());
						defectList.add(recordList);
					}
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("TrainingData.txt isn't a file.");
			}
		}else {
			System.out.println("TrainingData.txt isn't exist.");
		}	
		
		return defectList;
	}

	public static void main(String[] args) {
		
		//readTrainingData();
		//readTestData();
	}

}
