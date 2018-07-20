package dataprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NewDataList {
	
	public List<ArrayList<String>> getNewDataList(){
		File file = new File("/Users/carol/Desktop/数据挖掘/数据仓库第二次作业2016/任务一/newdata.txt");
		List<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
		
		
        //attribute
        if (file.isFile() && file.exists()){
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));
	            BufferedReader bufferedReader = new BufferedReader(read);
	
	            String lineText = null;
	            while((lineText = bufferedReader.readLine())!=null){
	            	//Person p = new Person();
	            	ArrayList<String> person = new ArrayList<String>();
	                String[] data = lineText.split(",");
	                for(int i=0; i< data.length;i++){
	                	person.add(data[i]);
	                }
	                dataList.add(person);
	            }
	            /*for(int i= 0; i<dataList.size();i++){
	            	System.out.println(dataList.get(i).get(0));
	            }*/
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else{
        	System.out.print("newdata.txt is not exist!");
        }
        return dataList;
	}
	
}
