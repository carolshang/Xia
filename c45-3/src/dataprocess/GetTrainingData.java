package dataprocess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import entity.Person;

/**
 * Created by carol on 11/21/16.
 */
public class GetTrainingData {


    public List<Person> getTrainingData(){

        String filepath = "/Users/carol/Desktop/数据挖掘/数据仓库第二次作业2016/任务一/adult.data.txt";

        List<Person> personlist = new ArrayList<Person>();
        try{
            File file = new File(filepath);

            if (file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);

                String lineText = null;
                while((lineText = bufferedReader.readLine())!=null){
                    Person p = new Person();
                   // System.out.println(lineText);
                    String[] trainingdata = lineText.split(",");

                   // System.out.println(trainingdata.length);

                   // System.out.println(trainingdata[1]);
                    p.setAge(trainingdata[0]);
                    p.setWorkclass(trainingdata[1]);
                    p.setFnlwgt(trainingdata[2]);
                    p.setEducation(trainingdata[3]);
                    p.setEducation_num(trainingdata[4]);
                    p.setMarital_status(trainingdata[5]);
                    p.setOccupation(trainingdata[6]);
                    p.setRelationship(trainingdata[7]);
                    p.setRace(trainingdata[8]);
                    p.setSex(trainingdata[9]);
                    p.setCapital_gain(trainingdata[10]);
                    p.setCapital_loss(trainingdata[11]);
                    p.setHours_per_week(trainingdata[12]);
                    p.setNative_country(trainingdata[13]);
                    p.setAnnual_salary(trainingdata[14]);

                    personlist.add(p);
                   // System.out.println(lineText);
                }

               /* for (Person p : personlist){
                    System.out.println(p.annual_salary);
                }*/
                read.close();
            }else {
                System.out.println("Can't find files!");
            }
        }
        catch (Exception e){
            System.out.println("Fail to read!");
            e.printStackTrace();
        }

        return personlist;
    }
}
