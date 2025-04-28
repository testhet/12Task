package Task_10_Expand_Given_Equation_using_code_list;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static final Set<String> icd10codeslist = new HashSet<>();

    public static void loadicd10codes() {

        try (BufferedReader br = new BufferedReader(new FileReader("/home/hetgoti/Downloads/Problem_Input/10/LatestIcd10Codes"))) {
            String line;

            while ((line = br.readLine()) != null) {
                icd10codeslist.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error Reading File: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        loadicd10codes();

        try (BufferedReader br = new BufferedReader(new FileReader("/home/hetgoti/Downloads/Problem_Input/10/Equations"))) {
            String str;
            HashMap<String, List<String>> eq_icd_pair = new HashMap<>();

            try {
                while ((str = br.readLine()) != null) {
                    HashSet<String> sorted_equetion_tokens = Sort_Tokens(str);

                    for (String s : sorted_equetion_tokens) {
                        if (!eq_icd_pair.containsKey(s)) {
                            eq_icd_pair.put(s, List_of_tokens(s));
                        }
                    }
                    System.out.println("------------------------------");
                    printer(eq_icd_pair, str, new HashSet<>());
                    System.out.println("------------------------------");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public static HashSet<String> Sort_Tokens(String str) {
        String[] s = str.split(" ");
        HashSet<String> sorted_equetion_tokens = new HashSet<>();
        for (String s1 : s) {
            if (s1.contains("*")) {
                sorted_equetion_tokens.add(s1);
            }
        }
        return sorted_equetion_tokens;
    }


    public static List<String> List_of_tokens(String s) {
        List<String> filteredIcdCodes = new ArrayList<>();
        Pattern pattern = Pattern.compile(s);

        for (String line : icd10codeslist) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                filteredIcdCodes.add(line);
            }
        }
        return filteredIcdCodes;
    }

    public static void printer(HashMap<String, List<String>> eq_icd_pair, String str, Set<String> expanded_eq) {

        for (Map.Entry<String, List<String>> e : eq_icd_pair.entrySet()) {
            List<String> listoftokens = e.getValue();
            for (String s : listoftokens) {
                String answer = str.replace(e.getKey(), s);
                if (answer.contains("*")) {
                    HashMap<String, List<String>> newMap = new HashMap<>(eq_icd_pair);
                    newMap.remove(e.getKey());
                    printer(newMap, answer, expanded_eq);
                } else {
                    if (!expanded_eq.contains(answer)) {
                        System.out.println(answer);
                        expanded_eq.add(answer);
                    }
                }
            }
        }

    }


}
