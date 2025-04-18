package Task_5_Working_With_XML_File;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path filePath = Paths.get("/home/hetgoti/Downloads/Problem_Input/5/");

        try (Stream<Path> files = Files.walk(filePath)) {
            files.filter(Files::isRegularFile).forEach(path -> {
                System.out.println(path.getFileName());
                try {
                    File inputFile = new File(path.toFile().toURI());
                    // create document builder
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();

                    //parse XMl data in document object
                    Document document = builder.parse(inputFile);
                    document.getDocumentElement().normalize();

                    NodeList icd10CmCodeList = document.getElementsByTagName("Icd10CmCode");
                    for (int i = 0; i < icd10CmCodeList.getLength(); i++) {
                        Node icdNode = icd10CmCodeList.item(i);
                        if (icdNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element icdElement = (Element) icdNode;


                            NodeList codeNodes = icdElement.getElementsByTagName("code");

                            Element bestCode = null;
                            int maxRank = Integer.MIN_VALUE;

                            for (int j = 0; j < codeNodes.getLength(); j++) {
                                Element codeElement = (Element) codeNodes.item(j);
                                String rankStr = codeElement.getAttribute("rank");

                                int rank;

                                try {
                                    rank = Integer.parseInt(rankStr);
                                } catch (NumberFormatException e) {
                                    throw new RuntimeException(e);
                                }

                                if (rank > maxRank) {
                                    maxRank = rank;
                                    bestCode = codeElement;
                                }
                            }
                            if (bestCode != null) {
                                String codeValue = bestCode.getAttribute("value");
                                String rankValue = bestCode.getAttribute("rank");

                                System.out.println("Code: " + codeValue);
                                System.out.println("Rank: " + rankValue);
                            } else {
                                System.out.println("Missing <code> element");
                            }

                            // Get the <code> element inside
//                            Element codeElement = (Element) icdElement.getElementsByTagName("code").item(0);
//                            if (codeElement != null) {
//                                String codeValue = codeElement.getAttribute("value");
//                                String rankValue = codeElement.getAttribute("rank");
//
//                                System.out.println("Code: " + codeValue);
//                                System.out.println("Rank: " + rankValue);
//                            } else {
//                                System.out.println("Missing <code> element");
//                            }

                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // Better than RuntimeException for debugging
        }
    }
}