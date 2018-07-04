package processer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Some Originator's and Owner's Name field is blank
   so through the relation between ID with Name of Originator, Owner to fill the blank field
*/
public class MatchIDName {
	
	
	public void BuildMap(List<String> recordList,String area[]) {
		Map idnameMap = new HashMap();
		
		for(int i=0; i < area.length;i++) {
			if(recordList.get(13).equals(area[i])) {
				
			}
		}
		idnameMap.put(recordList.get(3), recordList.get(13));
	}
	
	public void RecognizeID() {
		
	}
	
	public void RelateName() {
		
	}


	public static void main(String[] args) {

	}

}
