package fileRW;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileRead {

	public static void main(String[] args) {
	//	String filename = "./files/BTT Defects from CMVC.txt";
	//	String filename = "./files/defect1.txt";
		String filename = "./files/defect2.txt";
		
		File newfile = new File("./files/newfile1.txt");
		FileWriter fw = null;
		
		
		File cmvcf = new File(filename);
		BufferedReader br = null;
		int count = 0;
		if(cmvcf.exists()){
			if(cmvcf.isFile()){
				try {
					fw = new FileWriter(newfile);
					BufferedWriter bw = new BufferedWriter(fw); 
					
					br = new BufferedReader(new FileReader(cmvcf));
					String temp = null;
					while((temp = br.readLine()) != null){
						count++;
						/*if(count == 6633) {
							System.out.println("-----------------------");
						}*/
						bw.write(temp);
						bw.newLine();
						System.out.println(temp);
						
					}
					System.out.println(count);
					
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

}
