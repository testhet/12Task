package org.example;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatGPT10 {

    private static final Set<String> fileContent = new HashSet<>();

    public static void loadFileData() {
        String filePath = "/home/hetgoti/Downloads/Problem_Input/10/LatestIcd10Codes";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static List<String> List_of_Tokens(String s) {
        List<String> filteredList = new ArrayList<>();
        Pattern pattern = Pattern.compile(s);

        for (String line : fileContent) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                filteredList.add(line);
            }
        }
        return filteredList;
    }


    public static HashSet<String> Sort_Tokens(String str) {
        String[] s = str.split(" ");
        HashSet<String> mySet = new HashSet<>();
        for (String s1 : s) {

            if (s1.contains("*")) {
                mySet.add(s1);
            }
        }
        return mySet;
    }

    public static void printer(HashMap<String, List<String>> myMap, String str, Set<String> generated_equ) {

        for (Map.Entry<String, List<String>> e : myMap.entrySet()) {
            List<String> listOfString = e.getValue();
            for (String s : listOfString) {
                String ans = str.replace(e.getKey(), s);
                if (ans.contains("*")) {
                    HashMap<String, List<String>> newMap = new HashMap<>(myMap);
                    newMap.remove(e.getKey());
                    printer(newMap, ans ,generated_equ );
                } else {
                    if(!generated_equ.contains(ans)) {

                        System.out.println(ans);
                        generated_equ.add(ans);

                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        loadFileData();
        try {
            FileReader fr = new FileReader("/home/hetgoti/Downloads/Problem_Input/10/Equations");
            BufferedReader br = new BufferedReader(fr);
            String str;
            HashMap<String, List<String>> myMap = new HashMap<>();
            try {
                while ((str = br.readLine()) != null) {
                    HashSet<String> mySet = Sort_Tokens(str);
//                    System.out.println(mySet);

//                    HashMap<String, List<String>> myMap = new HashMap<>();

                    for (String s : mySet) {
                        if(!myMap.containsKey(s)){
                        myMap.put(s, List_of_Tokens(s));
                    }
                }

//                    for(Map.Entry<String, List<String>> e : myMap.entrySet()) {
//                    	System.out.print(e.getKey() + " " );
//                    	System.out.println(e.getValue());
//                    	System.out.println("---------------");
//
//                    }
                    System.out.println("--------------------------------");
                    printer(myMap, str , new HashSet<>());
                    System.out.println("--------------------------------");
                }
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}