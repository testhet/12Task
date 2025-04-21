package Task_10_Expand_Given_Equation_using_code_list;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Path icdCodes = Paths.get("/home/hetgoti/Downloads/Problem_Input/10/LatestIcd10Codes");
        Path equationFile = Paths.get("/home/hetgoti/Downloads/Problem_Input/10/Equations");

        try(BufferedReader br = Files.newBufferedReader(equationFile)){
            String line;

            while ((line = br.readLine()) != null){

                System.out.println(line);

            }
        }

    }
}
