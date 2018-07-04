package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processer.DeleteUselessColumn;

//The excute of the Project
public class Main {

	public static void main(String[] args) {
		String filename = "./files/BTT Defects from CMVC.txt";
		//	String filename = "./files/defect1.txt";
		//	String filename = "./files/defect2.txt";
			
		File newfile = new File("./files/newfile1.txt");

		
		List<String> recordList = new ArrayList<String>();
		DeleteUselessColumn duc = new DeleteUselessColumn();
	}

}
