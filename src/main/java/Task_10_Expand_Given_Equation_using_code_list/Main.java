package Task_10_Expand_Given_Equation_using_code_list;


import java.io.BufferedReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Path icdCodes = Paths.get("/home/hetgoti/Downloads/Problem_Input/10/LatestIcd10Codes");
        Path equationFile = Paths.get("/home/hetgoti/Downloads/Problem_Input/10/Equations");

        //read alll icd codes in list
        List<String> icdcodes = Files.readAllLines(icdCodes);


        Pattern pattern = Pattern.compile("([A-Z0-9]+)(?=\\.\\*)");

        try (BufferedReader br = Files.newBufferedReader(equationFile)) {
            String line;
            Set<String> basecode = new HashSet<>();

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    basecode.add(matcher.group(1));
                }

                String updateline;
                for (String base : basecode) {
                    for (String icd : icdcodes) {
                        if (icd.startsWith(base)) {
                            updateline = line.replaceAll(Pattern.quote(base + ".*"), Matcher.quoteReplacement(icd));
                            System.out.println(updateline);
                        }

                    }
                }

            }

        }

    }
}
