package Task_1_Remove_File_Which_Are_Present_In_List_From_GivenFolder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path filepath = Paths.get("/home/hetgoti/Downloads/Problem_Input/1/InputFolder");
        try(Stream<Path> entries = Files.walk(filepath)) {

            entries.forEach( entry -> System.out.println("Found: " +entry));


        }
        catch (IOException e) {
            System.out.println("Error walking directory: " + e.getMessage());
        }
    }


}
