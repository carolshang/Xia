package clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComponentBTTDivide {
	
	public HashMap<String, List<String>> getCompBTTMap() {
		File f = new File("./src/abstractProcess/componentBTTMatch.txt");
		HashMap<String,List<String>> compBTTMap = new HashMap<String,List<String>>();
		BufferedReader br;
		try {
			if(f.exists()) {
				br = new BufferedReader(new FileReader(f));
				String temp = null;
				while((temp = br.readLine()) != null) {

					String s[] = temp.split(":");
					String comp[] = s[1].split(",");
					List<String> compList = new ArrayList<String>();
					for(int i=0;i < comp.length;i++) {
						compList.add(comp[i]);
					}
					compBTTMap.put(s[0], compList);
				}
			}else {
				System.out.println("File componentBTTMatch.txt does not exist.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compBTTMap;
	}

	public static void main(String[] args) {
		ComponentBTTDivide cbd = new ComponentBTTDivide();
		HashMap<String,List<String>> compBTTMap = cbd.getCompBTTMap();
		for(String key:compBTTMap.keySet()) {
			List<String> value = compBTTMap.get(key);
			System.out.println(key+"---"+value.toString());
		}
	}

}
