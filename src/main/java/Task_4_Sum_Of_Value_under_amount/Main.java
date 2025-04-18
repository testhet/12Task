package Task_4_Sum_Of_Value_under_amount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path Folder_path = Paths.get("/home/hetgoti/Downloads/Problem_Input/4/");


        try (Stream<Path> datafiles = Files.walk(Folder_path)) {

            datafiles.filter(Files::isRegularFile)
                    .forEach(Main::findAmount);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findAmount(Path Path) {
        double fileAmountSum = 0.0;
        String fileName = Path.getFileName().toString();
        String delimiter;

        //select delimiter based on file type || dile ends with .txt OR .csv OR .tsv

        if (fileName.toLowerCase().endsWith(".csv")) {
            delimiter = ",";
        } else if (fileName.endsWith(".txt") || fileName.endsWith(".tsv")) {
            delimiter = "\t";
        } else {
            System.out.println("Unsupported file");
            return;
        }

        System.out.println("\n Reading" + fileName);

        try
                (BufferedReader br = new BufferedReader(new FileReader(Path.toFile()))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                return;
            }
            String[] headers = headerLine.split(delimiter, -1);
            int amountIndex = -1;


            //find amount column index

            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equalsIgnoreCase("Amount")) {
                    amountIndex = i;
                    break;
                }
            }
            if (amountIndex == -1) {
                System.out.println("Amount index is not found in" + fileName);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter, -1);

                if (amountIndex < values.length) {
                    String value = values[amountIndex].trim();

                    try {
                        double amount = Double.parseDouble(value);
                        fileAmountSum += amount;

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("Total Amount in File: " + fileName + " is " + fileAmountSum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
