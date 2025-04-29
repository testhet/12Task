package Task_12_Find_Conflict_and_nonconflict_entries_FromFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
                String[] s = line.toLowerCase().split("\\t");
                if (s.length == 3) {
                    String key = s[1] + "\t" + s[2];
                    if (myMap.containsKey(key)) {
                        myMap.get(key).add(s[0]);
                    } else {
                        System.out.println("Unknown Combination: " + key);
                    }
                } else {
                    System.out.println("Invalid Line: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("File reading failed", e);
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

        System.out.println("Unique Values:");
        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            for (String value : entry.getValue()) {
                if (unique.contains(value)) {
                    System.out.println(entry.getKey() + "\t" + value);
                }
            }
        }

        System.out.println("\nConflict Values:");
        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            for (String value : entry.getValue()) {
                if (conflict.contains(value)) {
                    System.out.println(entry.getKey() + "\t" + value);
                }
            }
        }
    }
}
