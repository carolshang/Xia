package prepareData;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CleanField {

	public static void main(String[] args) {
		CreateConnection cc = new CreateConnection();
		Connection conn = cc.createConnection();
		//query the defect data from database
		QueryData qd = new QueryData();
		List<List<String>> defectList = qd.queryForClean(conn);
		CleanField cf = new CleanField();
		List<List<String>> reldefectList = cf.updateReleaseField(defectList);
		//clean release results write into database
		WriteDataBase wdb = new WriteDataBase();
		wdb.insertCleanRelease(reldefectList, conn);
	}
	
	public List<List<String>> updateReleaseField(List<List<String>> defectList) {
		//List<List<String>> defectList = new ArrayList<List<String>>();
		List<List<String>> reldefectList = new ArrayList<List<String>>();
		List<String> recordList = new ArrayList<String>();
		int temp = 0;
	//	defectList = qd.queryForClean();
		for(int i=0;i<defectList.size();i++) {
		//	for(int j=0;j<defectList.get(i).size();j++) {
			String release = defectList.get(i).get(2);
			recordList = defectList.get(i);
			if(release.length() > 8 && Pattern.matches("[0-9]", release.charAt(8)+"")) {
				temp = Integer.parseInt(release.charAt(8)+"");
				switch(temp) {
				case 8:recordList.set(2, "composer8");break;
				case 7:recordList.set(2, "composer7");break;
				case 6:recordList.set(2, "composer6");break;
				case 5:recordList.set(2, "composer5");break;
				case 4:recordList.set(2, "composer4");break;
				default:recordList.set(2, "others");break;
			}
			}else {
				recordList.set(2, "others");
			}
			
			
			reldefectList.add(i, recordList);
		//	}
		}
		
		for(int i=0;i<reldefectList.size();i++) {
			System.out.println(reldefectList.get(i).toString());
		}
		return reldefectList;
	}
	
	//type field will show the defect type through abstract and component information
	public void addTypeField() {
		
	}

}
