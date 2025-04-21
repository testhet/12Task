package Task_2_Count_Number_of_line_starts_with_THE_from_text_file;




import java.nio.file.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;



public class Main {
    public static void main(String[] args) {

        Path filepath = Paths.get("/home/hetgoti/Downloads/Problem_Input/2/");

        try (Stream<Path> test = Files.walk(filepath)) {

            test.filter(Files::isRegularFile).forEach(path -> {
                        System.out.println("Reading file: " + path);
                        try {
                            List<String> lines = Files.readAllLines(path); // Read all lines

                            Pattern pattern = Pattern.compile("^\\s*the\\b", Pattern.CASE_INSENSITIVE);
                            long count = lines.stream()
                                    .filter(line -> pattern.matcher(line).find()).count();

                            System.out.println("Number Of Lines starting with 'the' \nUsing Regex : " + count);

                        } catch (Exception e) {
                            System.err.println("Could not read file: " + path);
                        }
                    });
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("END");
    }
}
