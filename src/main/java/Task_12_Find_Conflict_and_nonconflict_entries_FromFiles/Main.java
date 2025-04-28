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


        System.out.println("\nFinal Grouping (using Set):");
        for (Map.Entry<String, Set<String>> entry : myMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}