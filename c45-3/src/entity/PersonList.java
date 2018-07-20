package entity;

import java.util.List;

import dataprocess.GetTrainingData;


/**
 * Created by carol on 11/29/16.
 */
public class PersonList {

    int count = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0, count7 = 0;

    public List<Person> discretizeData(List<Person> personlist) {

        for (Person p : personlist) {
            // System.out.println(p.annual_salary);
            count++;
            //年龄
            discretizeAge(Integer.parseInt(p.getAge()),p);
            //fnlwgt
            discretizeFnlwgt(Long.parseLong(p.getFnlwgt().trim()),p);
            //education-num
            discretizeEducationNum(Integer.parseInt(p.getEducation_num().trim()), p);
            //capital-gain
            discretizeCapitalGain(Integer.parseInt(p.getCapital_gain().trim()), p);
            //capital-loss
            discretizeCapitalLoss(Integer.parseInt(p.getCapital_loss().trim()), p);
            //hours-per-week
            discretizeHoursPerWeek(Integer.parseInt(p.getHours_per_week().trim()), p);
        }
        System.out.println(count);
        return  personlist;
    }

    public void discretizeAge(int age, Person p){
        //age
        if (age < 20) {
            p.setAge("<20");
            //count1++;
        } else if (age >= 20 && age < 30) {
            p.setAge("20-30");
            //count2++;
        } else if (age >= 30 && age < 40) {
            p.setAge("30-40");
            //count3++;
        } else if (age >= 40 && age < 50) {
            p.setAge("40-50");
            //count4++;
        } else if (age >= 50 && age < 60) {
            p.setAge("50-60");
            //count5++;
        } else if (age >= 60 && age < 70) {
            p.setAge("60-70");
            //count6++;
        } else {
            p.setAge(">70");
            //count7++;
        }
    }

    public void discretizeFnlwgt(long fnlwgt, Person p){
        //fnlwgtç
        if (fnlwgt < 100000) {
            p.setFnlwgt("<100000");
            //count1++;
        } else if (fnlwgt >= 100000 && fnlwgt < 150000) {
            p.setFnlwgt("100000-150000");
            //count2++;
        } else if (fnlwgt >= 150000 && fnlwgt < 200000) {
            p.setFnlwgt("150000-200000");
            //count3++;
        } else if (fnlwgt >= 200000 && fnlwgt < 250000) {
            p.setFnlwgt("200000-250000");
            //count4++;
        } else if (fnlwgt >= 250000 && fnlwgt < 300000) {
            p.setFnlwgt("250000-300000");
            //count5++;
        } else {
            p.setFnlwgt(">300000");
            //count6++;
        }
    }

    public void discretizeEducationNum(int education_num, Person p){
        //education-numç
        if (education_num < 9) {
            p.setEducation_num("<9");
            //count1++;
        } else if (education_num >= 9 && education_num < 10) {
            p.setEducation_num("9-10");
            // count2++;
        } else if (education_num >= 10 && education_num < 11) {
            p.setEducation_num("10-11");
            //count3++;
        } else if (education_num >= 11 && education_num < 12) {
            p.setEducation_num("11-12");
            //count4++;
        } else {
            p.setEducation_num(">12");
            //count5++;
        }
    }

    public void discretizeCapitalGain(int capital_gain, Person p){
        //capital-gain
        if (Integer.parseInt(p.getCapital_gain().trim()) != 0) {
            p.setCapital_gain("!0");
            //count1++;
        }
    }

    public void discretizeCapitalLoss(int capital_loss, Person p){
        //capital-loss
        if (Integer.parseInt(p.getCapital_loss().trim()) != 0) {
            p.setCapital_loss("!0");
            //count3++;
        }
    }

    public void discretizeHoursPerWeek(int hours_per_week, Person p){
        //hours-per-week
        if (hours_per_week < 20) {
            p.setHours_per_week("<20");
            //count1++;
        } else if (hours_per_week >= 20 && hours_per_week < 30) {
            p.setHours_per_week("20-30");
            //count2++;
        } else if (hours_per_week >= 30 && hours_per_week < 40) {
            p.setHours_per_week("30-40");
            //count3++;
        } else if (hours_per_week >= 40 && hours_per_week < 50) {
            p.setHours_per_week("40-50");
            //count4++;
        } else if (hours_per_week >= 50 && hours_per_week < 60) {
            p.setHours_per_week("50-60");
            //count5++;
        } else {
            p.setHours_per_week(">60");
            //count6++;
        }
    }


    public static void main(String []args){

        GetTrainingData gtd = new GetTrainingData();
        List<Person> personlist = gtd.getTrainingData();

        PersonList ps = new PersonList();

        for (Person p : ps.discretizeData(personlist)){
            System.out.println(p.age+p.fnlwgt+p.education_num+p.capital_gain+p.capital_loss+p.hours_per_week);
        }

    }
}
