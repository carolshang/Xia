package Aproir;

import java.util.ArrayList;
import java.util.List;
/**
 * Input : datalist , min_support
 * Output : support , confidence , frequent record
 * 
 * get L1 frequent itemsets
 * loop : get candidate subsets
 * procedure
*/
public class Aproir {

	public static void main(String[] args) {
		
		List<List<String>> testList = new ArrayList<List<String>>();
		List<String> tempList = null;
		String a[][] = {
				{"I1","I2","I5"},
				{"I2","I4"},
				{"I2","I3"},
				{"I1","I2","I4"},
				{"I1","I3"},
				{"I2","I3"},
				{"I1","I3"},
				{"I1","I2","I3","I5"},
				{"I1","I2","I3"}};
		for(int i=0;i<a.length;i++) {
			tempList = new ArrayList<String>();
			for(int j=0;j<a[i].length;j++) {
				tempList.add(a[i][j]);
			}
			testList.add(i,tempList);
		}
		
		//set min support
		
		//if support less than min support, then delete the record from frequent set L
		
		//join L1 and L1 = L2 , delete the support less than min support from L2, loop until Ln is null
		
	}
	
	

}
