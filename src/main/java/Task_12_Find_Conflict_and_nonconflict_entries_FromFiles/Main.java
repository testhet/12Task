package Task_12_Find_Conflict_and_nonconflict_entries_FromFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        String filePath = "/home/hetgoti/Downloads/Problem_Input/12/Inputfile";
        Map<String, Set<String>> myMap = readAndBuildMap(filePath);
        Map<String, Integer> countMap = getCountMap(myMap);
        printResults(myMap, countMap);
    }


    private static Map<String, Set<String>> readAndBuildMap(String filePath) {
        Map<String, Set<String>> myMap = new HashMap<>();
        myMap.put("yes\tyes", new HashSet<>());
        myMap.put("yes\tno", new HashSet<>());
        myMap.put("no\tyes", new HashSet<>());
        myMap.put("no\tno", new HashSet<>());

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.toLowerCase().split("\\t",2);
                    if (myMap.containsKey(s[1])) {
                        myMap.get(s[1]).add(s[0]);
                    } else {
                        System.out.println("Unknown Combination: " + s[1]);
                    }
            }
        } catch (IOException e) {
            System.err.println("File Not Found: "+ e.getMessage());
        }
        return myMap;
    }


    private static Map<String, Integer> getCountMap(Map<String, Set<String>> myMap) {
        Map<String, Integer> countMap = new HashMap<>();
        for (Set<String> values : myMap.values()) {
            for (String val : values) {
                countMap.put(val, countMap.getOrDefault(val, 0) + 1);
            }
        }
        return countMap;
    }
    private static void printResults(Map<String, Set<String>> myMap, Map<String, Integer> countMap) {
        Set<String> unique = new HashSet<>();
        Set<String> conflict = new HashSet<>();

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) unique.add(entry.getKey());
            else conflict.add(entry.getKey());
        }

        // For collecting sorted output
        List<String> uniqueOutput = new ArrayList<>();
        List<String> conflictOutput = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            for (String value : entry.getValue()) {
                String outputLine =  value+ "\t" + entry.getKey();
                if (unique.contains(value)) {
                    uniqueOutput.add(outputLine);
                } else if (conflict.contains(value)) {
                    conflictOutput.add(outputLine);
                }
            }
        }


        uniqueOutput.sort(Comparator.comparing(line -> line.split("\\t")[0]));
        conflictOutput.sort(Comparator.comparing(line -> line.split("\\t")[0]));


        System.out.println("Unique Values:");
        for (String line : uniqueOutput) {
            System.out.println(line);
        }

        System.out.println("\nConflict Values:");
        for (String line : conflictOutput) {
            System.out.println(line);
        }
    }


}