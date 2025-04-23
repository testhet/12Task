package Task_11_Retrive_all_BMI_Value_from_Given_document_UsingREGEX;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
       Path filePath = Paths.get("/home/hetgoti/Downloads/Problem_Input/11/TextDocument");
        Pattern pattern1 = Pattern.compile("\\bBMI\\b.*?(\\d+(?:\\.\\d)?)");
        Pattern pattern2 = Pattern.compile("(?i)\\bB\\w*\\s+M\\w*\\s+I\\w*\\b.*?(\\d+(?:\\.\\d)?)");
        Pattern pattern3 = Pattern.compile("\\bBMI+(\\d+(?:\\.\\d)?)");

       try(BufferedReader br = Files.newBufferedReader(filePath)){
            int count = 0;
           String lines;
           while ((lines = br.readLine()) != null){
               count++;
               Matcher matcher =  pattern1.matcher(lines);
               Matcher matcher1 = pattern2.matcher(lines);
               Matcher matcher2 = pattern3.matcher(lines);
               if(matcher.find()){
                   System.out.println("Line: "+count+" BMI: "+ matcher.group(1));
               } else if (matcher1.find()) {
                   System.out.println("Line: "+count+" BMI: "+ matcher1.group(1));
               } else if (matcher2.find()) {
                   System.out.println("Line: "+count+" BMI: "+matcher2.group(1) );
               } else {
                   System.out.println("no match found");
               }
           }

       }
    }
}
