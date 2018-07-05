package database;

import java.util.List;

public class WriteIntoMySql {
	
	public String createSql(List<String> recordList,int i) {
		
		String sql = "INSERT INTO defect VALUES ('30423', 'build', 'composer8103B', 'btt', 'cdlbuild', 'closed','2013/09/06 13:58:16',\r\n" + 
				"'link file from 8103 to 8103B to backup 8103 9/6 devlivery','2013/09/06 13:58:16','2013/09/06 16:23:11','program_defect',\r\n" + 
				"'Family Admin ID','BTT Build','2013/09/06 13:58:39',3);";
		
		return sql;
	}

	public static void main(String[] args) {
		

	}

}
