package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chatgpt10 {
    private static final Set<String> codesList = new HashSet<>();

    public static void main(String[] args) {
        getAllCodes();
        try {
            FileReader fr = new FileReader("/home/hetgoti/Downloads/Problem_Input/10/Equations");
            BufferedReader br = new BufferedReader(fr);
            String str;
            try {
                while ((str = br.readLine()) != null) {
                    HashSet<String> Set = SortTokens(str);
                    HashMap<String, List<String>> Map = new HashMap<>();
                    for (String s : Set) {
                        Map.put(s, listTokens(s));
                    }

                    System.out.println(" ");
                    expandEquations(Map, str, new HashSet<>());
                    System.out.println(" ");
                }
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void getAllCodes() {
        String filePath = "/home/hetgoti/Downloads/Problem_Input/10/LatestIcd10Codes";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                codesList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static List<String> listTokens(String s) {
        List<String> filteredList = new ArrayList<>();
        Pattern pattern = Pattern.compile(s);
        for (String line : codesList) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                filteredList.add(line);
            }
        }
        return filteredList;
    }

    public static HashSet<String> SortTokens(String str) {
        String[] s = str.split(" ");
        HashSet<String> Set = new HashSet<>();
        for (String s1 : s) {

            if (s1.contains("*")) {
                Set.add(s1);
            }
        }
        return Set;
    }


    public static void expandEquations(HashMap<String, List<String>> myMap, String str, Set<String> generated_equ) {
        for (Map.Entry<String, List<String>> e : myMap.entrySet()) {
            List<String> listOfString = e.getValue();
            for (String s : listOfString) {
                String ans = str.replace(e.getKey(), s);
                if (ans.contains("*")) {
                    HashMap<String, List<String>> newMap = new HashMap<>(myMap);
                    newMap.remove(e.getKey());
                    expandEquations(newMap, ans, generated_equ);
                } else {
                    if (!generated_equ.contains(ans)) {
                        System.out.println(ans);
                        generated_equ.add(ans);
                    }
                }
            }
        }
    }
}