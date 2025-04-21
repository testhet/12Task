package Task_9_Compare_CM_Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path mainFolder = Paths.get("/home/hetgoti/Downloads/Problem_Input/9/CM1/");
        Path dummyFolder = Paths.get("/home/hetgoti/Downloads/Problem_Input/9/CM2/");

        try(Stream<Path> mainFile = Files.walk(mainFolder)){

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

          mainFile.filter(Files::isRegularFile)
                  .forEach(path -> {
                      System.out.println("Comparing "+path.getFileName()+" with Dummy File");

                  });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
