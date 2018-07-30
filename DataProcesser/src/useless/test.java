package useless;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
		try {
			Date date = new java.sql.Date(dateFormat.parse("11/21/05 16:37").getTime());
			
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
