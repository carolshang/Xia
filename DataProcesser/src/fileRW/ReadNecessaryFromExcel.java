package fileRW;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadNecessaryFromExcel {

	public List<List<String>> readExcel(File file,List<List<String>> defectList) {

		try {
			InputStream	inputstream = new FileInputStream(file.getCanonicalPath());
			//jxl supply Workbook class
			Workbook wb = Workbook.getWorkbook(inputstream);
			//get the first sheet
			Sheet sheet = wb.getSheet(0);
			Set<String> flagSet = null;
			List<String> recordList = null;
			int count = 0;

			//get the all rows number
			for(int i=0; i< sheet.getRows(); i++) {
				//get the all columns number
				recordList = new ArrayList<String>();
				flagSet = new HashSet<String>();
				for(int j=0; j< sheet.getColumns(); j++) {
					//get the info by rows
					String cellInfo = sheet.getCell(j, i).getContents();
					recordList.add(j, cellInfo);
					if(cellInfo.equals("")) {
						flagSet.add("false");
					}else {
						flagSet.add("true");
					}
				}
			//	System.out.println(recordList);
				/*for(int t=0 ; t<recordList.size(); t++) {
					if(recordList.get(t).equals("")) {
						break;
					}
				}*/
				if(flagSet.contains("true")) {
					defectList.add(recordList);
				}
			//	System.out.println();
/*				if(count == 21) {
					System.out.println("---------------");
				}*/
				count++;
			}
			System.out.println(count);
		} catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return defectList; 
	}

}
