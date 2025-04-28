package Task_12_Find_Conflict_and_nonconflict_entries_FromFiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {




    public static void main(String[] args) throws FileNotFoundException {


        HashMap<String, Set<String>> myMap = new HashMap<>();

        // Initialize keys
        myMap.put("yes\tyes",new HashSet<>());
        myMap.put("yes\tno", new HashSet<>());
        myMap.put("no\tyes", new HashSet<>());
        myMap.put("no\tno", new HashSet<>());


        try(BufferedReader br = new BufferedReader(new FileReader("/home/hetgoti/Downloads/Problem_Input/12/Inputfile"))){
            String strr;


            while ((strr = br.readLine()) != null){
                try{
                    String str = strr.toLowerCase();
                    String[] s = str.split("\\t");
               if(s.length == 3){
                   String key = s[1] + "\t" + s[2];
                   if(myMap.containsKey(key)){
                       myMap.get(key).add(s[0]);
                   } else {
                       System.out.println("Unknown Combination" + key);
                   }
               } else {
                   System.out.println("Invalid Line" + str);
               }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String , Integer> countValue = new HashMap<>();
        for (Set<String> set : myMap.values()){
            for (String value : set){
                countValue.put(value, countValue.getOrDefault(value , 0) + 1);
            }
        }


        // To separate unique and conflict values
        Set<String> uniqueValues = new HashSet<>();
        Set<String> conflictValues = new HashSet<>();

        // Iterate through the countMap to categorize values
        for (Map.Entry<String, Integer> entry : countValue.entrySet()) {
            if (entry.getValue() == 1) {
                uniqueValues.add(entry.getKey());  // Value appears only once in the entire HashMap
            } else {
                conflictValues.add(entry.getKey());  // Value appears in multiple sets (conflict)
            }
        }

        // Print the results for unique values, along with the key
        System.out.println("Unique Values:");
        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();
            for (String value : values) {
                if (uniqueValues.contains(value)) {
                    System.out.println(key + " -> " + value);  // Print key and unique value
                }
            }
        }

        // Print the results for conflict values, along with the key
        System.out.println("\nConflict Values:");
        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();
            for (String value : values) {
                if (conflictValues.contains(value)) {
                    System.out.println(key + " -> " + value);  // Print key and conflict value
                }
            }
        }
    }
}