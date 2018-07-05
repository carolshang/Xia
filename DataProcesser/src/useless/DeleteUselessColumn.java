package useless;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DeleteUselessColumn {
	
	String area[] = {"china cdl","china","Toronto","Taiwan","Spain","Rochester","RTP","IBMTaiwan","China Beijing","China","BeiJing China","Barcelona","Austin"};
	//This is a suite
	public List<String> ProcessAllField(List<String> recordList) {
		
		return recordList;
	}
	
	public List<String> DeleteBlankBetComponentRelease(List<String> recordList) {
		
		return recordList;
	}
	//delete the useless field
	public List<String> RemoveLevel(List<String> recordList) {
		
		//remove  Level column from the records
		if(Pattern.matches("^L.[0-9]*[a-f]$", recordList.get(7))) {
			recordList.remove(7);
		}
		if(Pattern.matches("^L.[0-9]*[0-9]$", recordList.get(7))) {
			recordList.remove(7);
		}
		//because of some field is fill with 1,2,3,4
		if(Pattern.matches("[0-9]*", recordList.get(7))){
			recordList.remove(7);
		}
		//because of some field is fill with 020607,020621,05/29/02
		if(Pattern.matches("^0.*[127]$", recordList.get(7))) {
			recordList.remove(7);
		}
		//because of some field is fill with 20110222a
		if(Pattern.matches("^2.*a$", recordList.get(7))) {
			recordList.remove(7);
		}
		//because of some field is fill with L021223a.1601
		if(recordList.get(7).contains(".")) {
			if(Pattern.matches("^L.[0-9]*[a-b]$", recordList.get(7).substring(0, 8))) {
				recordList.remove(7);
			}
		}
		//because of some field is fill with L021223a_was7
		if(recordList.get(7).contains("_")) {
			if(Pattern.matches("^L.[0-9]*[a-b]$", recordList.get(7).substring(0, 8))) {
				recordList.remove(7);
			}
		}
		if(recordList.get(7).equals("wo")) {
			recordList.remove(7);
		}
		return recordList;
	}
	public List<String> RemoveAge(List<String> recordList) {
		//remove Age column(if the eighth column is number, so remove)
		if(Pattern.matches("[0-9]*", recordList.get(7))){
			recordList.remove(7);
		}
		return recordList;
	}
	//execute twice for two column
	public List<String> RemoveReferenceDuplicate(List<String> recordList) {
		//remove  Reference  column and Duplicate  column
		boolean flag = Pattern.matches("(\\d{4})/(\\d+)/(\\d+).(\\d+):(\\d+):(\\d+)", recordList.get(9));
		if(!flag) {
			recordList.remove(9);
		}
		return recordList;
	}
	
	
	public List<String> RemoveEnvironment(List<String> recordList) {
		//remove  Environment  column
		//match the field contains windows/Windows
		if(Pattern.matches(".indows.*", recordList.get(9))) {
			recordList.remove(9);
		}
		if(Pattern.matches(".indows.*", recordList.get(10))) {
			recordList.remove(10);
		}
		if(recordList.get(9).equals("unix") || recordList.get(9).equals("a") || recordList.get(9).equals("OS/390")
				|| recordList.get(9).equals("NT40/WSAD5.0") || recordList.get(9).equals("1") || recordList.get(9).equals("33295")
				|| recordList.get(9).equals("04/27")) {
			recordList.remove(9);
		}
		if(recordList.get(10).equals("unix") || recordList.get(10).equals("a") || recordList.get(10).equals("OS/390") 
				|| recordList.get(10).equals("NT40/WSAD5.0") || recordList.get(10).equals("1") || recordList.get(10).equals("33295")
				|| recordList.get(10).equals("04/27")) {
			recordList.remove(10);
		}
		if(Pattern.matches("^W.*", recordList.get(9))) {
			recordList.remove(9);
		}
		if(Pattern.matches("^W.*", recordList.get(10))) {
			recordList.remove(10);
		}
		if(Pattern.matches("^S.*", recordList.get(9))) {
			recordList.remove(9);
		}
		if(Pattern.matches("^S.*", recordList.get(10))) {
			recordList.remove(10);
		}
		if(Pattern.matches("^R.*", recordList.get(9))) {
			recordList.remove(9);
		}
		if(Pattern.matches("^R.*", recordList.get(10))) {
			recordList.remove(10);
		}
		if(Pattern.matches("^L.*", recordList.get(9))) {
			recordList.remove(9);
		}
		if(Pattern.matches("^L.*", recordList.get(10))) {
			recordList.remove(10);
		}
		if(Pattern.matches("^A.*", recordList.get(9))) {
			recordList.remove(9);
		}
		if(Pattern.matches("^A.*", recordList.get(10))) {
			recordList.remove(10);
		}
		return recordList;
	}
	
	public List<String> SetDateOfClosedOrCanceled(List<String> recordList) {
		//remove  DateOfLastUpdate  column  
		//filled  which DateOfClosedOrCanceled is null with the DateOfLastUpdate
		//if DateOfClosedOrCanceled is not null, remove DateOfLastUpdate
		if(Pattern.matches("(\\d{4})/(\\d+)/(\\d+).*", recordList.get(9))) {
			if(!Pattern.matches("(\\d{4})/(\\d+)/(\\d+).*", recordList.get(10))) {
				recordList.add(10, recordList.get(9));
			}
		}
		/*if(Pattern.matches("(\\d{4})/(\\d+)/(\\d+).*", recordList.get(10))) {
			recordList.remove(10);
		}*/
		return recordList;
	}
	public List<String> FillAnswerBlank(List<String> recordList){
		for(int i = 0;i < area.length; i++) {
			//if Answer is null, the tenth is Originator's Area, so set null when Answer is null
			if(recordList.get(11).equals(area[i])) {
				recordList.add(11, "null");
			}
		}
		return recordList;
	}
	
	public List<String> RemoveOriginatorArea(List<String> recordList) {
		//remove  Originator's Area  column
		
		for(int i = 0;i < area.length; i++) {
			//if Answer is null, the tenth is Originator's Area, so set null when Answer is null
			if(recordList.get(11).equals(area[i])) {
				recordList.add(11, "null");
			}
			//remove Originator's Area
			if(recordList.get(12).contains(area[i])) {
				recordList.remove(12);
			}
		}
		return recordList;
	}
	
	public List<String> AddOriginatorName(List<String> recordList) {
		//Originator's Name in some records is null, and all this records Originator's ID is "zhangyj"
		if(recordList.get(3).equals("zhangyj")) {
			recordList.add(12, "Zhang Yan Jie");
		}
		return recordList;
	}
	
	public List<String> RemoveOwnerArea(List<String> recordList) {
		//remove  Owner's Area  column
		
		for(int i = 0;i < area.length; i++) {
			if(recordList.get(13).contains(area[i])) {
				recordList.remove(13);
			}
		}
		return recordList;
	}
	
	public List<String> SetDateAcceptedReturned(List<String> recordList) {
		//add the DateAcceptedReturned which is null with DateOfLastUpdate
		if(!Pattern.matches("(\\d{4})/(\\d+)/(\\d+).*", recordList.get(14))) {
			recordList.add(14, recordList.get(10));
		}
		return recordList;
	}
	
	//remove all useless field of the record end
	public List<String> RemoveOthersUselessField(List<String> recordList) {
		if(recordList.size() > 16) {
			for(int i=17; i < recordList.size();) {
				recordList.remove(i);
			}
		}
		//remove DateOfLastUpdate
		recordList.remove(10);
		//remove Prefix
		recordList.remove(14);
		return recordList;
	} 
	

		
	public static void main(String[] args) {
	//	boolean flag = Pattern.matches("^L.[0-9]*[a-b]$", "L234324a");
	//	boolean flag = Pattern.matches("(\\d{4})/(\\d+)/(\\d+).*", "2013/07/22 13:33:48");
		boolean flag = Pattern.matches("^[A-Z].* [A-Z].* [A-Z].*","BeiJing China");
		System.out.println(flag);

	}

}
