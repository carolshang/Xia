package useless;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileReadToSplitField {

	public static void ReadFile(String filename,String newfilename) {
		File cmvcf = new File(filename);
		BufferedReader br = null;
		File newfile = new File(newfilename);
		FileWriter fw = null;
		List<String> recordList = new ArrayList<String>();
		int count = 0;
		if(cmvcf.exists()){
			if(cmvcf.isFile()){
				try {
					br = new BufferedReader(new FileReader(cmvcf));
					fw = new FileWriter(newfile);
					BufferedWriter bw = new BufferedWriter(fw);
					String temp = null;
					while((temp = br.readLine()) != null){
						count++;
						//split record into each field
						String [] eachRecord = temp.split("   ");
						for(String field : eachRecord){
							recordList.add(field.trim());
						}
						bw.write(recordList.toString() + "\r\n");
						bw.flush();
						System.out.println(recordList.toString());
					}
					System.out.println(count);
					br.close();
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("BTT Defects from CMVC.txt isn't a file.");
			}
		}else {
			System.out.println("BTT Defects from CMVC.txt isn't exist.");
		}	
	}
	public static void main(String[] args) {
	//	String filename = "./files/BTT Defects from CMVC.txt";
		String filename = "./files/defect1.txt";
	//	String filename = "./files/defect2.txt";
		String newfilename = "./files/splitedDefect.txt";
		ReadFile(filename,newfilename);
	}

}
