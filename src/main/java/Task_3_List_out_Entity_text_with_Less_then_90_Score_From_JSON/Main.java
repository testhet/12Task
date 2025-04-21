package Task_3_List_out_Entity_text_with_Less_then_90_Score_From_JSON;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Path Folder_path = Paths.get("/home/hetgoti/Downloads/Problem_Input/3/AmazonMed_Json/");
        ObjectMapper mapper = new ObjectMapper();

        try (Stream<Path> jsonsFolder = Files.walk(Folder_path)) {
            jsonsFolder.filter(Files::isRegularFile)
                    .forEach(Path -> {
                        System.out.println("\n\nReading " + Path.getFileName() + " File\n __________________________");

                        try {
//                  readallBytes read File as entire file as a byte of array
                            String content = new String(Files.readAllBytes(Path)).trim();

                            if (content.isEmpty()) {
                                System.out.println("Skipping empty file: " + Path.getFileName());
                                return; // skip this file
                            }
                            JsonNode rootNode = mapper.readTree(content);
                            JsonNode entitiesNode = rootNode.get("Entities");


                            if (entitiesNode != null && entitiesNode.isArray()) {
                                for (JsonNode entity : entitiesNode) {
                                    JsonNode ScoreNode = entity.get("Score");
                                    JsonNode TextNode = entity.get("Text");


                                    if (ScoreNode != null && ScoreNode.asDouble() < 0.90) {

                                        System.out.println("Score: " + ScoreNode.asDouble());
                                        System.out.println("Text: " + TextNode.asText() + "\n");


                                    }
                                }

                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
