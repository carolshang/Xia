package useless;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class FileReadToProcess {
	
	
	public static void main(String[] args) {/*
		File cmvcf = new File("./files/splitedDefect.txt");
		BufferedReader br = null;
		File newfile = new File("./files/processed/processedDefect.txt");
		FileWriter fw = null;
		List<String> recordList = new ArrayList<String>();
		
		DeleteUselessColumn duc = new DeleteUselessColumn();
		
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
						//processer all the records
						
						recordList = duc.
						
						
		
						
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
				System.out.println("splitedDefect.txt isn't a file.");
			}
		}else {
			System.out.println("splitedDefect.txt isn't exist.");
		}	
	*/}

}
