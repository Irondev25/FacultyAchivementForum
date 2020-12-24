package com.irondev25.facultyachivementforum;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface GlobalVars {
    public static final String API_URL = "http://530e1e13a26e.ngrok.io";
//    public static final String API_URL = "http://184.72.166.226:8000";
//    public static final String API_URL = "http://10.0.2.2:8000";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static HashMap<String,Integer> deptPairList = new HashMap<>();
    public static ArrayList<String> deptList = new ArrayList<>();
    public static HashMap<Integer,String> reverseDeptMap = new HashMap<>();

    public static HashMap<String,String> genderMap = new HashMap<>();
    public static ArrayList<String> genderList = new ArrayList<>();
    public static HashMap<String,String> reverseGenderMap = new HashMap<>();

    public static HashMap<String, String> paperTypes = new HashMap<>();
    public static HashMap<String, String> revPaperTypes = new HashMap<>();
    public static ArrayList<String> paperList = new ArrayList<>();

    public static void genPaperTypes(){
        if(paperTypes.size()!=0) return;

//        ('N','National'),
//        ('IN','International'),
//        ('IEEE-N','IEEE-National'),
//        ('IEEE-IN','IEEE-International')
        paperTypes.put("N","National");
        paperTypes.put("IN","International");
        paperTypes.put("IEEE-N","IEEE-National");
        paperTypes.put("IEEE-IN","IEEE-International");

        for(String key:paperTypes.keySet()){
            revPaperTypes.put(paperTypes.get(key),key);
            paperList.add(paperTypes.get(key));
        }
    }

    public static void genDept(){
        if(deptPairList.size() != 0) return;
        deptPairList.put("Civil Engineering",1);
        deptPairList.put("Mechanical Engineering",2);
        deptPairList.put("Electrical and Electronics Engineering",3);
        deptPairList.put("Electronics and Communication Engineering",4);
        deptPairList.put("Industrial Engineering and Management",5);
        deptPairList.put("Computer Science and Engineering",6);
        deptPairList.put("Electronics and Telecommunication Engineering",7);
        deptPairList.put("Information Science and Engineering",8);
        deptPairList.put("Electronics and Instrumentation Engineering",9);
        deptPairList.put("Medical Electronics",10);
        deptPairList.put("Chemical Engineering",11);
        deptPairList.put("Bio-Technology",12);
        deptPairList.put("Computer Applications (MCA)",13);
        deptPairList.put("Management Studies and Research Centre",14);
        deptPairList.put("Mathematics Department",15);
        deptPairList.put("Physics Department",16);
        deptPairList.put("Chemistry Department",17);
        deptPairList.put("Aerospace Engineering",18);

        for(String key:deptPairList.keySet()){
            deptList.add(key);
            reverseDeptMap.put(deptPairList.get(key),key);
        }

        Collections.sort(deptList);
    }

    public static void genGender(){
        if (genderMap.size() !=0 ) return;
        genderMap.put("Male","m");
        genderMap.put("Female","f");
//        genderMap.put("Transgender","t");
        genderMap.put("Other","o");

        for(String key:genderMap.keySet()){
            genderList.add(key);
            reverseGenderMap.put(genderMap.get(key), key);
        }
    }

    public static void genVals(){
        genDept();
        genGender();
        genPaperTypes();
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static HashMap<String,Integer> getDeptMap(){
        return deptPairList;
    }

    public static ArrayList<String> getDeptList(){
        return deptList;
    }

    public static HashMap<String,String> getGenderMap(){
        return genderMap;
    }

    public static ArrayList<String> getGenderList(){
        return genderList;
    }

    public static HashMap<String,String> getPaperTypes() { return paperTypes; }
}

