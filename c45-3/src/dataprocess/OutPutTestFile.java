package dataprocess;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import entity.Person;
import entity.PersonList;

/**
 * Created by carol on 12/11/16.
 */
public class OutPutTestFile {

    public static void main(String [] args){
        GetTestData gtd = new GetTestData();
        List<Person> personlist = new ArrayList<Person>();
        personlist = gtd.getTestData();
        PersonList pl = new PersonList();
        List<Person> datalist = pl.discretizeData(personlist);

        try {
            FileOutputStream os = new FileOutputStream(new File("testdata.txt"));
            PrintStream ps = new PrintStream(os);
            int count=0;
            for (Person p : datalist){
					/*if(p.occupation.trim().equals("?") || p.workclass.trim().equals("?")){
						count++;
						continue;
					}else{*/
                ps.println(p.age+","+p.workclass.trim()+","+p.fnlwgt+","+p.education.trim()+","
                        +p.education_num+","+p.marital_status.trim()+","+p.occupation.trim()+","
                        +p.relationship.trim()+","+p.race.trim()+","+p.sex.trim()+","+p.hours_per_week+","
                        +p.annual_salary.trim().replace(".",""));
                //System.out.println(p.age+p.fnlwgt+p.education_num+p.capital_gain+p.capital_loss+p.hours_per_week);
                //}
            }
            System.out.println(count);
            System.out.println("Succeed write into testdata.txt!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
