package clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordsBTTDivide {

	public HashMap<String, List<String>> getWordsBTTMap() {
		File f = new File("./src/abstractProcess/WordsBTTMatch.txt");
		HashMap<String,List<String>> wordBTTMap = new HashMap<String,List<String>>();
		BufferedReader br;
		try {
			if(f.exists()) {
				br = new BufferedReader(new FileReader(f));
				String temp = null;
				while((temp = br.readLine()) != null) {

					String s[] = temp.split(":");
					String word[] = s[1].split(",");
					List<String> wordList = new ArrayList<String>();
					for(int i=0;i < word.length;i++) {
						wordList.add(word[i]);
					}
					wordBTTMap.put(s[0], wordList);
				}
			}else {
				System.out.println("File WordsBTTMatch.txt does not exist.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordBTTMap;
	}

	public static void main(String[] args) {
		WordsBTTDivide cbd = new WordsBTTDivide();
		HashMap<String,List<String>> wordBTTMap = cbd.getWordsBTTMap();
		for(String key:wordBTTMap.keySet()) {
			List<String> value = wordBTTMap.get(key);
			System.out.println(key+"---"+value.toString());
		}
	}

}
