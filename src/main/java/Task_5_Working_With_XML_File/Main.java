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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path filePath = Paths.get("/home/hetgoti/Downloads/Problem_Input/5/");

        try (Stream<Path> files = Files.walk(filePath)) {
            files.filter(Files::isRegularFile).forEach(path -> {
                System.out.println("reading : " + path.getFileName());
                try {

                    //can use File inputFile = path.toFile() also but toURI() will add safety like converts space in to %20 encode # and ?
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
                            Element bestCode = getElement(icdElement);


                            String codeValue = bestCode.getAttribute("value");
                            String rankValue = bestCode.getAttribute("rank");

                            System.out.print("Code: " + codeValue);
                            System.out.println(" Rank: " + rankValue);

                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Element getElement(Element icdNode) {


        NodeList codeNodes = icdNode.getElementsByTagName("code");
        List<Element> codeWithMaxRank = new ArrayList<>();

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

                codeWithMaxRank.clear();
                codeWithMaxRank.add(codeElement);
            } else if (rank == maxRank) {

                codeWithMaxRank.add(codeElement);
            }
            if (!codeWithMaxRank.isEmpty()) {
                Random random = new Random();
                int arrSize = codeWithMaxRank.size();
                int randomCode = random.nextInt(arrSize);
                bestCode = codeWithMaxRank.get(randomCode);
            }

        }
        return bestCode;
    }
}