package Apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Input : datalist , min_support
 * Output : support , confidence , frequent record
 * 
 * get L1 frequent itemsets
 * loop : get candidate subsets
 * procedure
*/
public class Apriori {
	
	//set min support
    final static double MIN_SUPPORT = 2 ; //support
    final static double MIN_CONFIDENCE = 0.5; //conference

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
        //get first one-item candidate set 
         Map<List<String>,Integer> cItemMap = new HashMap<List<String>,Integer>();
         for(int i=0; i < testList.size();i++) {
        	 for(int j=0; j < testList.get(i).size();j++) {
        		 List<String> tList = new ArrayList<String>();
        		 tList.add(testList.get(i).get(j));
        		 if(!cItemMap.containsKey(tList)) {
        			 cItemMap.put(tList, 1);
        		 }else {
        			 int keycount = cItemMap.get(tList) + 1;
        			 cItemMap.put(tList, keycount);
        		 }
        	 }
         }
         //get frequent one-item set
         List<List<String>> fItemList = new ArrayList<List<String>>();
         for(List<String> key:cItemMap.keySet()) {
        	 if(cItemMap.get(key) > MIN_SUPPORT) {
            	 continue;
             }else {
            	 fItemList.add(key);
             }
         }
         
         //get next candidate
         for(int i=0; i<fItemList.size();i++) {
        	 String item1 = fItemList.get(i).toString();
        	 for(int k=0; k<fItemList.get(i).size();k++) {
        		 String a1 = fItemList.get(i).toString();
        		 
        		 for(int j=0; j<fItemList.size();j++) {
        			 boolean flag = true;
            		 for(int h=0; h<fItemList.get(i).size();h++) {
            			 String a2 = fItemList.get(j).get(h);
            			 if(a1.equals(a2)) {
            				 flag = false;
            			 }
                	 }
                 }
        	 }
         }
         List<List<String>> nextcItemSet = new ArrayList<List<String>>();  //next candidate item set
         for(int i=0; i<fItemList.size();i++) {
        	 List<String> tList = new ArrayList<String>();
        	 for(int k=0; k<fItemList.get(i).size();k++) {
        		 tList.add(fItemList.get(i).get(k));
        		 for(int j=i+1; j<fItemList.size();j++) {  //loop 0 - 1....k; 1 - 2....k; 2 - 3.....k;....; k-1 - k;
        			 for(int h=0; h<fItemList.get(j).size();h++) {
        				 tList.add(fItemList.get(j).get(h));
        				 if(isSubSetInLastFrequent(tList, fItemList)) {  //if all the subsets exist in the last frequents
        					 List<String> itemlist = new ArrayList<String>(); //save the new candidate item into candidate item set
        					 for(int p=0;p < tList.size();p++) {
        						 itemlist.add(tList.get(p));
        					 }
        					 if(isExist(itemlist,nextcItemSet)) {
        						 nextcItemSet.add(tempList);
        					 }
        				 }
        				 //TODO
        				 tList.remove(tList.size() - 1); 
        			 }
        		 }
        	 }
         }
        
		//if support less than min support, then delete the record from frequent set L
 		
		//join L1 and L1 = L2 , delete the support less than min support from L2, loop until Ln is null
		
	}
	public static boolean isExist(List<String> itemlist,List<List<String>> nextcItemSet) {
		boolean flag = true;
		for(int i=0;i<nextcItemSet.size();i++) {
			if(itemlist.equals(nextcItemSet.get(i))) {
				flag = false;
			}
		}
		return flag;
	}
	
	public static boolean isSubSetInLastFrequent(List<String> tList,List<List<String>> fItemList) {
		boolean flag = false;
		for(int i=0; i<tList.size();i++) {
			List<String> subList = new ArrayList<String>();
			for(int j=0; j<tList.size();j++) {
				if(i != j) {
					subList.add(tList.get(j));
				}
				for (int k = 0; k < fItemList.size(); k++) {
					if (subList.equals(fItemList.get(k))) {//subset in the k-1 frequent set
						flag = true;
						break;
					}
				}
			}
			if(flag == false) { //there is at least one subset is not in k-1 frequent set
				return false;
			}
		}
		return flag;
	}
	
	

}
