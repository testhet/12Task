package Task_2_Count_Number_of_line_starts_with_THE_from_text_file;

import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;



public class Main {
    public static void main(String[] args) {

        Path filepath = Paths.get("/home/hetgoti/Downloads/Problem_Input/2/");

        try (Stream<Path> test = Files.walk(filepath)) {

            test.forEach(Path -> {
                        System.out.println("Reading file: " + Path);
                        try {
                            List<String> lines = Files.readAllLines(Path); // Read all lines

                            long count = lines.stream()
                                    .filter(line -> line.trim().toLowerCase().startsWith("the"))
                                    .count();
                            System.out.println("Number Of Lines starting with 'the': " + count);

                        } catch (Exception e) {
                            System.err.println("Could not read file: " + Path);

                        }
                    });

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("END");
    }
}
