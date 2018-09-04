package Apriori;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
    final int MIN_SUPPORT = 2 ; //support
    final double MIN_CONFIDENCE = 0.5; //conference

	public static void main(String[] args) {
		Apriori apri = new Apriori();
		//initial data
		List<Set<String>> dataList = Apriori.initialData();

		//findFrequentItem all Set
		Map<Integer, List<Set<String>>> resMap = apri.findFrequentItemList(dataList, apri.MIN_SUPPORT);
		
		//output the frequentItem
		for(int itemCount:resMap.keySet()) {
			List<Set<String>> frequentItemList = resMap.get(itemCount);
			System.out.println("Item-"+itemCount+"--frequent item list--------------");
			for(int i=0; i<frequentItemList.size();i++) {
				System.out.println(frequentItemList.get(i).toString());
			}
		}
		
		//Confidence calculate
	}
	public static List<Set<String>> initialData(){
		List<Set<String>> dataList = new ArrayList<Set<String>>();
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
			Set<String> tempList = new HashSet<String>();
			for(int j=0;j<a[i].length;j++) {
				tempList.add(a[i][j]);
			}
			dataList.add(i,tempList);
		}
		return dataList;
	}
	
	
	public Map<Integer, List<Set<String>>> findFrequentItemList(List<Set<String>> dataList,int min_support) {
		
		//save all frequent list of each count item
		Map<Integer, List<Set<String>>> resMap = new TreeMap<Integer, List<Set<String>>>();
		
		
		//get first one-item candidate set 
		List<Set<String>> oneItemFrenquentList = findOneItemFrequentList(dataList,min_support);
		resMap.put(1, oneItemFrenquentList);
		
		int preFrequentListSize = 1;
		List<Set<String>> preFrequentList = oneItemFrenquentList;
		
		while(!preFrequentList.isEmpty()) {
			int curFrequentListSize  = preFrequentListSize + 1;
			//get L candidate item list from L-1 frequent item list
			//the candidate list in current loop
			List<Set<String>>  currentCandidateList = aprioriGenerateCandidate(preFrequentList);
			//the frequent list in current loop
			List<Set<String>> currentFrequentList = new ArrayList<Set<String>>();
			currentFrequentList = getCurrFrequentList(dataList,currentCandidateList,min_support);
			if(!currentFrequentList.isEmpty()) {
				resMap.put(curFrequentListSize, currentFrequentList);
			}
			preFrequentListSize = curFrequentListSize;
			preFrequentList = currentFrequentList;
			
		}
		return resMap;
		
        
	}
	public List<Set<String>> getCurrFrequentList(List<Set<String>> dataList,List<Set<String>>  currentCandidateList,int min_support) {
		List<Set<String>> currentFrequentList = new ArrayList<Set<String>>();
		
		//count the frequent of the candidate list , get candidate map in current loop
		Map<Set<String>,Integer> curCandidateMap = new HashMap<Set<String>,Integer>();
		curCandidateMap = getCandidateMap(dataList,currentCandidateList);
		
		//remove item which count less than min_support
		int a = 0;
		for(Set<String> tempList : curCandidateMap.keySet()) {
			 if(curCandidateMap.get(tempList) > min_support) {
				 currentFrequentList.add(a, tempList);
				 a++;
			 }
		}
				
		return currentFrequentList;
	}
	public Map<Set<String>,Integer> getCandidateMap(List<Set<String>> dataList, List<Set<String>> currentCandidateList) {
		Map<Set<String>,Integer> curCandidateMap = new HashMap<Set<String>,Integer>();
		
		for(int i=0; i < currentCandidateList.size();i++) {
			int candlistSize = currentCandidateList.get(i).size();
			Set<String> candidateTempList = currentCandidateList.get(i);
			for(int j=0; j < dataList.size(); j++) {
				Set<String> dataTempList = dataList.get(j);
				//compare the item
				int count = 0;
				for(String candidateStr:candidateTempList) {
					if(dataTempList.contains(candidateStr)) {
						count++;
					}
				}
				if(count == candlistSize) {  //the item in candlist is all in the datalist.get(j), so the candidatelist add 1
					if(!curCandidateMap.containsKey(candidateTempList)) {
						curCandidateMap.put(candidateTempList, 1);
					}else {
						int freCount = curCandidateMap.get(candidateTempList);
						curCandidateMap.replace(candidateTempList, freCount+1);
					}
				}
			}
		}
		
		return curCandidateMap;
	}
	
	public List<Set<String>> aprioriGenerateCandidate(List<Set<String>> preFrequentList) {
		List<Set<String>>  currentFrequentList  = new ArrayList<Set<String>>();
		//get next candidate
     //   boolean flag = true;
        for(int i=0; i<preFrequentList.size();i++) {
        	 Set<String> temList = new HashSet<String>();  //save the new item list, for example pre is 2-Item, save 3-Item
        	 Set<String> list1 = preFrequentList.get(i);
       		 boolean isfreqSubSet = true;
       		 for(int j=i+1; j<preFrequentList.size();j++) {
       			Set<String> list2 = preFrequentList.get(j);
       			 if(!list1.equals(list2) && joinOrNot(list1,list2)) {
       				 //join list1 and list2
       				 int count = 0;
       				 try {
       					 //TODO
						temList = (Set<String>)deepCopy((List<String>)list1);
						String tempStr = null;
	       				 for(String list2Str:list2) {
	       					 if(!list1.contains(list2Str)) {
	       						 count++;
	       						tempStr = list2Str;
	       					 }
	       				 }
	       				if(count == 1){
	   						temList.add(tempStr);
	   					 }
					} catch (Exception e) {
						e.printStackTrace();
					}
       				 
       				 //prune
       				 //循环访问join的结果，如果其中任意一个结果的子集是不频繁子集，那么该结果删除，否则，加入到新的频繁项集里
       				 List<Set<String>>  subSetList = getSubSet(temList);
   					 for(int q=0;q<subSetList.size();q++) {
   						 if(!preFrequentList.contains(subSetList.get(q))) {
   							isfreqSubSet = false;
   							break;
   						 }
   					 }
   					 int a = 0;
             		 if(isfreqSubSet && !currentFrequentList.contains(temList)) {
             			currentFrequentList.add(a,temList); 
             			a++;
             		 }
       			 }
            } 
        }
        return currentFrequentList;
	}

	public List<Set<String>> findOneItemFrequentList(List<Set<String>> dataList,int min_support) {
		 Map<Set<String>,Integer> cItemMap = new HashMap<Set<String>,Integer>();
         for(int i=0; i < dataList.size();i++) {
        	 for(String tempstr:dataList.get(i)) {
        		 Set<String> tList = new HashSet<String>();
        		 tList.add(tempstr);
        		 
        		 if(!cItemMap.containsKey(tList)) {
        			 cItemMap.put(tList, 1);
        		 }else {
        			 int keycount = cItemMap.get(tList) + 1;
        			 cItemMap.put(tList, keycount);
        		 }
        	 }
         }
         //output one-item set
         //TODO
         /*for(List<String> tlist:cItemMap.keySet()) {
        	 System.out.println(tlist.toString() + " -- "+cItemMap.get(tlist));
         }*/
         System.out.println("One Item Frequent List--------");
         //get frequent one-item set
         List<Set<String>> fItemList = new ArrayList<Set<String>>();
         for(Set<String> key:cItemMap.keySet()) {
        	 System.out.println(key+" - "+cItemMap.get(key));
        	 if(cItemMap.get(key) > min_support) {
        		 fItemList.add(key);
             }
         }
         
         //output one-item set
         //TODO
         /*System.out.println("Frequent item set -----------------------");
         for(List<String> flist:fItemList) {
        	 System.out.println(flist.toString());
         }*/
         return fItemList;
	}
	
	public List<Set<String>> getSubSet(Set<String> temList){
		List<Set<String>> subList = new ArrayList<Set<String>>();
		if(temList.size() > 1) {

			int a = 0;
			for(String temStr:temList) {
				Set<String> llist;
				try {
					//TODO
					llist = (Set<String>)deepCopy((List<String>)temList);
					llist.remove(temStr);
					subList.add(a,llist);
					a++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return subList;
	}
	public List<String> deepCopy(List<String> src) throws Exception {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(src);  

	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    @SuppressWarnings("unchecked")  
	    List<String> dest = (List<String>) in.readObject();  
	    return dest;  
	}  

	public boolean joinOrNot(Set<String> list1,Set<String> list2) {
		int count=0;
		// 若两个项集的阶不同，则不能连接生成L阶项集
		if (list2.size() != list1.size()) {
			return false;
		}else {
			// 假定项集的阶为L-1，在项有序的前提下，当且仅当两个项集的前L-2个项相同
			// 而本项集的第L-1个项different from另一个项集的第L-1个项时，可以连接生成L阶项集
			for(String tempstr:list2) {
				//String item = list2.get(i);
				if(!list1.contains(tempstr)) {
					count++;
				}
			}
			if(count == 1) {
				return true;
			}else {
				return false;
			}
			/*for(int i=0; i<list1.size();i++) {
				String item1 = list1.get(i);
				for(int j=0; j<list2.size();j++) {
					String item2 = list2.get(j);
					if(!item1.equals(item2)) {
						count++;
					}
				}
			}*/
		}
	}
	public boolean isExist(List<String> itemlist,List<List<String>> nextcItemSet) {
		boolean flag = true;
		for(int i=0;i<nextcItemSet.size();i++) {
			if(itemlist.equals(nextcItemSet.get(i))) {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean isSubSetInLastFrequent(List<String> tList,List<List<String>> fItemList) {
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
