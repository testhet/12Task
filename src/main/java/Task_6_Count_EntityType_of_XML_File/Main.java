package Task_6_Count_EntityType_of_XML_File;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path xmlFiles = Paths.get("/home/hetgoti/Downloads/Problem_Input/6/");

        Map<String, String> entityTags = new LinkedHashMap<>();
        entityTags.put("med", "MEDICINE");
        entityTags.put("meas", "BODY_MEASUREMENT");
        entityTags.put("lab", "LABORATORY_DATA");
        entityTags.put("procedure", "PROCEDURE");
        entityTags.put("problem", "PROBLEM");
        entityTags.put("anatomicalStructure", "ANATOMICAL_STRUCTURE");
        entityTags.put("finding", "FINDING");
        entityTags.put("bodyFunction", "BODY_FUNCTION");
        entityTags.put("medicalDevice", "MEDICAL_DEVICE");
        entityTags.put("modifier", "MODIFIER");


        try (Stream<Path> XMLfile = Files.walk(xmlFiles)) {

            XMLfile.filter(Files::isRegularFile)
                    .forEach(Path -> {
                                System.out.println("\n" + "Reading " + Path.getFileName() + "\n");

                                File xml_file = Path.toFile();

                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                try {
                                    DocumentBuilder builder = factory.newDocumentBuilder();
                                    Document doc = builder.parse(xml_file);

                                    for (Map.Entry<String, String> entry : entityTags.entrySet()) {
                                        String tag = entry.getKey();
                                        String label = entry.getValue();
                                        NodeList nodeList = doc.getElementsByTagName(tag);
                                        System.out.println(label + " Entity count : " + nodeList.getLength());
                                    }

                                } catch (ParserConfigurationException | IOException | SAXException e) {
                                    System.err.println("Error processing file: " + Path.getFileName());
                                    e.printStackTrace();
                                }

//
//                try {
//                    DocumentBuilder builder = factory.newDocumentBuilder();
//                    Document doc = builder.parse(xml_file);
//                    NodeList medList = doc.getElementsByTagName("med");
//                    NodeList measList = doc.getElementsByTagName("meas");
//                    NodeList labDataList = doc.getElementsByTagName("lab");
//                    NodeList problemList = doc.getElementsByTagName("problem");
//                    NodeList procedureList = doc.getElementsByTagName("procedure");
//                    NodeList anatomicalStructureList = doc.getElementsByTagName("anatomicalStructure");
//                    NodeList findingList = doc.getElementsByTagName("finding");
//                    NodeList bodyFunction = doc.getElementsByTagName("bodyFunction");
//                    NodeList medDevice = doc.getElementsByTagName("medicalDevice");
//                    NodeList modifierList = doc.getElementsByTagName("modifier");
//
//                    System.out.println("MEDICINE Entity count : "+medList.getLength());
//                    System.out.println("BODY_MEASUREMENT Entity count : "+measList.getLength());
//                    System.out.println("LABORATORY_DATA Entity count : "+labDataList.getLength());
//                    System.out.println("PROCEDURE Entity count : "+procedureList.getLength());
//                    System.out.println("PROBLEM Entity count : "+problemList.getLength());
//                    System.out.println("ANATOMICAL_STRUCTURE Entity count : "+anatomicalStructureList.getLength());
//                    System.out.println("FINDING Entity count : "+findingList.getLength());
//                    System.out.println("BODY_FUNCTION Entity count : "+bodyFunction.getLength());
//                    System.out.println("MEDICAL_DEVICE Entity count : "+medDevice.getLength());
//                    System.out.println("MODIFIER Entity count : "+modifierList.getLength());
//                    System.out.println("-----------------------------------------------------------------------------------\n\n-----------------------------------------------------------------------------------");
//
//                } catch (SAXException | IOException | ParserConfigurationException e) {
//                    throw new RuntimeException(e);
//                }


                            }
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
