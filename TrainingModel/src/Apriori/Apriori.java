package Apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Input : datalist , min_support
 * Output : support , confidence , frequent record
 * 
 * get L1 frequent itemsets
 * loop : get candidate subsets
 * procedure
*/
public class Apriori {

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
		
		 boolean endTag = false ;
         List<List<String>> cItemset ; //save candidate item set
         List<List<String>> ckItemset ;
         Map<List<String>, Integer> lItemset ;  //save frequent item set
         Map<List<String>,Integer>  lkItemset ;
         List<List<String>> record = new ArrayList<List<String>> () ;
		//set min support
         final double MIN_SUPPORT = 2 ; //support
         Map<List<String>, Double > confItemset = new HashMap<List<String>,Double >() ; //conference
		//if support less than min support, then delete the record from frequent set L
 		
		//join L1 and L1 = L2 , delete the support less than min support from L2, loop until Ln is null
		
	}
	
	

}
