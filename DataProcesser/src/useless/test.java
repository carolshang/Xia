package useless;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) {
		/*DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
		try {
			Date date = new java.sql.Date(dateFormat.parse("11/21/05 16:37").getTime());
			
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
        Map<String, Integer> map = new HashMap();
        map.put("1", 8);
        map.put("2", 12);
        map.put("3", 53);
        map.put("4", 33);
        map.put("5", 11);
        map.put("6", 3);
        map.put("7", 3);
        Collection<Integer> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        System.out.println(obj[obj.length-1]);
        System.out.println(obj);

	}

}
