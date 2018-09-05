package Apriori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


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
    final int MIN_SUPPORT = 5 ; //support
    final double MIN_CONFIDENCE = 0.6; //conference

	public static void main(String[] args) {
		Apriori apri = new Apriori();
		//initial data
		List<Set<String>> dataList = Apriori.initialData();
		File outfile = new File("./files/apriori/testResult.txt");
		try {
			FileWriter fw = new FileWriter(outfile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//findFrequentItem all Set
			Map<Integer, Map<Set<String>, Integer>> resMap = apri.findFrequentItemList(dataList, apri.MIN_SUPPORT);
			
			//output the frequentItem
			for(int itemCount:resMap.keySet()) {
				Map<Set<String>, Integer> tempMap = resMap.get(itemCount);
			//	System.out.println("Item-"+itemCount+"--frequent item list--------------");
				bw.write("Item-"+itemCount+"--frequent item list--------------" + "\r\n");
				for(Set<String> itemSet:tempMap.keySet()) {
					//System.out.println(itemSet.toString() + "---------" + tempMap.get(itemSet));
					bw.write(itemSet.toString() + "---------" + tempMap.get(itemSet) + "\r\n");
				}
			}
			//get association rules
			Map<String,Number> associationRules = apri.getAssociationRules(resMap,bw); 
			Map<String,Number> usefulAR = new HashMap<String,Number>();
			for(String rules:associationRules.keySet()) {
				double confidence = (double)associationRules.get(rules);
				if(confidence > apri.MIN_CONFIDENCE) {
					usefulAR.put(rules,confidence);
				}
			}
			//output the rules match min_confidience
		//	System.out.println("The useful association rules ------------------------------");
			bw.write("The useful association rules ------------------------------" + "\r\n");
			for(String rules:usefulAR.keySet()) {
			//	System.out.println(rules+" --- "+usefulAR.get(rules));
				bw.write(rules+" --- "+usefulAR.get(rules) + "\r\n");
			}
			
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public Map<String,Number> getAssociationRules(Map<Integer, Map<Set<String>, Integer>> resMap, BufferedWriter bw) {
	
		Map<String,Number> associationRules = new HashMap<String,Number>(); 
		try {
			bw.write("Association Rules is following --------------" + "\r\n");
//			System.out.println("Association Rules is following --------------");
			for(int itemcount:resMap.keySet()) {
				Map<Set<String>, Integer> tempMap = resMap.get(itemcount);
				for(Set<String> itemSet:tempMap.keySet()) {
					if(itemSet.size() > 1) {
						//get the support of one set ,for example the support of {1,2} is 4
						double value1 = tempMap.get(itemSet);
						//get the subset of itemSet,  {1,2} = {1},{2}
						Map<Set<String>,Integer> subSetMap = getAllSubSets(itemSet,resMap);
						if(!subSetMap.isEmpty()) {
							for(Set<String> subItemSet:subSetMap.keySet()) {
								//get the support of each sub set of {1}=5, {2}=6
								int value2 = subSetMap.get(subItemSet);
								double confidence = new BigDecimal(value1/value2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								Set<String> remainSet = removeSubSet(itemSet,subItemSet);
							//	System.out.println(subItemSet+" => "+remainSet+" ------ "+confidence);
								bw.write(subItemSet+" => "+remainSet+" ------ "+confidence + "\r\n");
								associationRules.put(subItemSet+" => "+remainSet, confidence);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return associationRules;
	}
	
	public Set<String> removeSubSet(Set<String> itemSet,Set<String> subItemSet) {
		Set<String> remainSet = itemSet.stream().map(String::new).collect(Collectors.toSet());
		for(String subItem:subItemSet) {
			remainSet.remove(subItem);
		}
		return remainSet;
	}
	public Map<Set<String>,Integer> getAllSubSets(Set<String> itemSet, Map<Integer, Map<Set<String>, Integer>> resMap) {
		//the subset excepts itself
		Map<Set<String>,Integer> subSetMap = new HashMap<Set<String>,Integer>();
		//get sub sets
		for(String item:itemSet) {
			Set<String> subSet = itemSet.stream().map(String::new).collect(Collectors.toSet());
			subSet.remove(item);
			int subSetCount = 0;
			//get the count of the subSets in the final result map
			Map<Set<String>, Integer> eachResMap = resMap.get(subSet.size());
			for(Set<String> itemsubSet:eachResMap.keySet()) {
				if(itemsubSet.equals(subSet)) {
					subSetCount = eachResMap.get(itemsubSet);
					break;
				}
			}
			subSetMap.put(subSet, subSetCount);
		}
		return subSetMap;
	}
	
	public static List<Set<String>> initialData(){
		List<Set<String>> dataList = new ArrayList<Set<String>>();
		/*String a[][] = {
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
		}*/
		/*QueryData qd = new QueryData();
		dataList = qd.queryDataForApriori();*/
		File file = new File("./TestData.txt");
		if(file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String temp = null;
				//int t = 0;
				while ((temp = br.readLine()) != null) {
					String a[] = temp.split(",");
					Set<String> tempSet = new HashSet<String>();
					for(int i=0;i<a.length;i++) {
						tempSet.add(a[i].trim());
					}
					dataList.add(tempSet);
					//t++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
	
	public Map<Integer, Map<Set<String>, Integer>> findFrequentItemList(List<Set<String>> dataList,int min_support) {
		
		//save all frequent list of each count item
		Map<Integer, Map<Set<String>, Integer>> resMap = new TreeMap<Integer, Map<Set<String>, Integer>>();
		//get first one-item candidate set 
		Map<Set<String>, Integer> oneItemFrenquentMap = findOneItemFrequentList(dataList,min_support);
		resMap.put(1, oneItemFrenquentMap);
		List<Set<String>> oneItemFrenquentList =  new ArrayList<Set<String>>();
		for(Set<String> onefset:oneItemFrenquentMap.keySet()) {
			oneItemFrenquentList.add(onefset);
		}
		int preFrequentListSize = 1;
		List<Set<String>> preFrequentList = oneItemFrenquentList;
		
		while(!preFrequentList.isEmpty()) {
			int curFrequentListSize  = preFrequentListSize + 1;
			//get L candidate item list from L-1 frequent item list
			//the candidate list in current loop
			List<Set<String>>  currentCandidateList = aprioriGenerateCandidate(preFrequentList);
			//the frequent list in current loop
			List<Set<String>> currentFrequentList = new ArrayList<Set<String>>();
			Map<Set<String>, Integer> curFrequentMap = new HashMap<Set<String>, Integer>();
			curFrequentMap = getCurrFrequentList(dataList,currentCandidateList,min_support);
			for(Set<String> curSet:curFrequentMap.keySet()) {
				currentFrequentList.add(curSet);
			}
			if(!currentFrequentList.isEmpty()) {
				resMap.put(curFrequentListSize, curFrequentMap);
			}
			preFrequentListSize = curFrequentListSize;
			preFrequentList = currentFrequentList;
			
		}
		return resMap;
		
        
	}
	public Map<Set<String>,Integer> getCurrFrequentList(List<Set<String>> dataList,List<Set<String>>  currentCandidateList,int min_support) {
		Map<Set<String>,Integer> curFrequentMap = new HashMap<Set<String>,Integer>();
		
		//count the frequent of the candidate list , get candidate map in current loop
		Map<Set<String>,Integer> curCandidateMap = new HashMap<Set<String>,Integer>();
		curCandidateMap = getCandidateMap(dataList,currentCandidateList);
		
		//remove item which count less than min_support
		for(Set<String> tempList : curCandidateMap.keySet()) {
			 if(curCandidateMap.get(tempList) >= min_support) {
				 curFrequentMap.put(tempList, curCandidateMap.get(tempList));
			 }
		}
				
		return curFrequentMap;
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
						temList = list1.stream().map(String::new).collect(Collectors.toSet());
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

	public Map<Set<String>,Integer> findOneItemFrequentList(List<Set<String>> dataList,int min_support) {
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
         Map<Set<String>,Integer> fItemMap = new HashMap<Set<String>,Integer>();
         System.out.println("One Item Frequent List--------");
         //get frequent one-item set
      //   List<Set<String>> fItemList = new ArrayList<Set<String>>();
         for(Set<String> key:cItemMap.keySet()) {
        	 System.out.println(key+" - "+cItemMap.get(key));
        	 if(cItemMap.get(key) >= min_support) {
        		 fItemMap.put(key, cItemMap.get(key));
             }
         }
         return fItemMap;
	}
	
	public List<Set<String>> getSubSet(Set<String> temList){
		List<Set<String>> subList = new ArrayList<Set<String>>();
		if(temList.size() > 1) {

			int a = 0;
			for(String temStr:temList) {
				Set<String> llist;
				try {
					llist = temList.stream().map(String::new).collect(Collectors.toSet());
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
	
/*	@Override   
	protected String clone() {   
		String clone = null;   
	    try{   
	        clone = (String) super.clone();   
	          
	    }catch(CloneNotSupportedException e){   
	        throw new RuntimeException(e);  // won't happen   
	    }   
	      
	    return clone;   
	} 
	public List<String> deepCopySet(List<String> src) throws Exception {  

	    Collection<String> copy = new HashSet<String>(src.size());   
	    Iterator<String> iterator = src.iterator();   
	    while(iterator.hasNext()){   
	        copy.add(iterator.next().clone());   
	    }  

	}  */

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
