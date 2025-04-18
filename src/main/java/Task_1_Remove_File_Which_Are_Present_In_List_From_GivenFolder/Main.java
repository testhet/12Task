package Task_1_Remove_File_Which_Are_Present_In_List_From_GivenFolder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path filepath = Paths.get("/home/hetgoti/Downloads/Problem_Input/1/InputFolder");
        Path deleteListPath = Paths.get("/home/hetgoti/Downloads/Problem_Input/1/DeleteFiles.txt");

        try {
//read text from delete list text
            List<String> lines = Files.readAllLines(deleteListPath);
            Set<String> filesToDelete = lines.stream()
                    .map(String::trim)//remove extra space from both side
                    .collect(Collectors.toSet());// collect all trimed string in to files to delete set string

//Files.get() do not traverse through sub folder so use Files.walk()
            try (Stream<Path> entries = Files.walk(filepath)) {


                //Files::isRegularFile not required in our case just for validation
                entries.filter(Files::isRegularFile)

//lambdas (path-> {}) are like arrow function in java script (path => {})

                        //filter out files using files to delete set
                        .filter(path -> filesToDelete.contains(path.getFileName().toString()))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                                System.out.println("deleted" + path);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            entries.close();
                        });


            }
        } catch (IOException e) {
            System.out.println("Error walking directory: " + e.getMessage());
        }
    }


}
