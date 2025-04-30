package Task_12_Find_Conflict_and_nonconflict_entries_FromFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        String filePath = "/home/hetgoti/Downloads/Problem_Input/12/Inputfile";
        Map<String, Set<String>> myMap = readAndBuildMap(filePath);
        printUniqueAndConflictingValues(myMap);
    }

    private static Map<String, Set<String>> readAndBuildMap(String filePath) {
        Map<String, Set<String>> myMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.toLowerCase().split("\\t", 2);
                if (s.length < 2) continue; // skip malformed lines
                String key = s[0].trim();
                String value = s[1].trim();
                myMap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
            }
        } catch (IOException e) {
            System.err.println("File Not Found: " + e.getMessage());
        }

        return myMap;
    }

    private static void printUniqueAndConflictingValues(Map<String, Set<String>> myMap) {

        List<String> uniqueOutput = new ArrayList<>();
        List<String> conflictOutput = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();

            if (values.size() == 1) {
                String unique = key + "\t\t" + values.iterator().next();
                uniqueOutput.add(unique);
            } else {
                for (String value : values) {
                    conflictOutput.add(key + "\t\t" + value);
                }
            }
        }

        Collections.sort(uniqueOutput);
        Collections.sort(conflictOutput);

        System.out.println("Unique Values:");
        for (String unique : uniqueOutput) {
            System.out.println(unique);
        }


        System.out.println("Conflicting Values:");
        for (String conflict : conflictOutput) {
            System.out.println(conflict);
        }
    }
}