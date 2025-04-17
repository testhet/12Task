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

        try (Stream<Path> Jsons = Files.walk(Folder_path)) {
            Jsons.filter(Files::isRegularFile).forEach(Path -> {
                System.out.println("Reading " + Path.getFileName() + " File");

                try {

                    String content = new String(Files.readAllBytes(Path)).trim();

                    if (content.isEmpty()) {
                        System.out.println("Skipping empty file: " + Path.getFileName());
                        return; // skip this file
                    }
                    JsonNode rootNode = mapper.readTree(content);
                    JsonNode entitiesNode = rootNode.get("Entities");


//
////                    If want to print whole object with score less then 90%
//
//                    if(entitiesNode != null && entitiesNode.isArray()){
//                        for (JsonNode entity : entitiesNode) {
//                            JsonNode ScoreNode = entity.get("Score");
//
//                            if (ScoreNode != null && ScoreNode.asDouble() < 0.90){
//                                String filteredEntity = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
//                                System.out.println(filteredEntity);
//                            }
//                        }
//                    }
//
//


                    // If want to print only text and score
//
//
                    if (entitiesNode != null && entitiesNode.isArray()) {
                        for (JsonNode entity : entitiesNode) {
                            JsonNode ScoreNode = entity.get("Score");
                            JsonNode TextNode = entity.get("Text");


                            if (ScoreNode != null && ScoreNode.asDouble() < 0.90) {

                                System.out.println("Score: " + ScoreNode.asDouble());
                                System.out.println("Text: " + TextNode.asText());
                                System.out.println("__________________________");

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
