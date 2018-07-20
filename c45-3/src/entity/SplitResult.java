package entity;

import java.util.ArrayList;
import java.util.List;

public class SplitResult {
	//TODO
	public double getInfoGainRatio() {
		return GainRatio;
	}
	//TODO
	public void setInfoGainRatio(double infoGainRatio) {
		this.GainRatio = infoGainRatio;
	}
	double GainRatio;
	double InfoGain;
	public double getInfoGain() {
		return InfoGain;
	}
	//TODO
	public void setInfoGain(double infoGain) {
		this.InfoGain = infoGain;
	}
	String AttributeName;
	public String getAttributeName() {
		return AttributeName;
	}
	//TODO
	public void setAttributeName(String attributeName) {
		AttributeName = attributeName;
	}
	ArrayList<String> splitDescribe;
	List<List<DataEntry>> splitData;
	public SplitResult(){
		splitDescribe = new ArrayList<String>();
		splitData = new ArrayList<List<DataEntry>>();
	}
	//TODO
	public List<DataEntry> getSplitData(int index) {
		return splitData.get(index);
	}
	//TODO
	public void addData(List<DataEntry> splitData) {
		this.splitData.add(splitData);
	}
	//TODO
	public void addDescribe(String describe){
		splitDescribe.add(describe);
	}
	//TODO
	public String getDescribe(int index){
		return splitDescribe.get(index);
	}
	public int length(){
		return splitDescribe.size();
	}
	public String toString(){
		String returnMessage = "====================\n";
		returnMessage += AttributeName+"\n";
		returnMessage+="InfoGain:"+this.InfoGain+"\n";
		returnMessage+="IfnoGainRatio:"+this.GainRatio+"\n";
		returnMessage+= "Split on:\n";
		for(String s:splitDescribe){
			returnMessage+=" "+s;
		}
		returnMessage += "\n====================\n";
		return returnMessage;
	}
}
