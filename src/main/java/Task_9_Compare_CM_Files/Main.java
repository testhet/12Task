package Task_9_Compare_CM_Files;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path mainFolder = Paths.get("/home/hetgoti/Downloads/Problem_Input/9/CM1/");
        Path dummyFolder = Paths.get("/home/hetgoti/Downloads/Problem_Input/9/CM2/");

        try (Stream<Path> mainFile = Files.walk(mainFolder)) {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            mainFile.filter(Files::isRegularFile)
                    .forEach(path -> {
                        System.out.println("Comparing " + path.getFileName() + " with Dummy File");
                        try {
                            File inputfile1 = path.toFile();

                            Document documentOrignal = builder.parse(inputfile1);
                            documentOrignal.getDocumentElement().normalize();
                            List<String> code1 = extractCode(documentOrignal);


                            //find matching file from cm2 folderr
                            Path dummyfilePath = dummyFolder.resolve(path.getFileName());
                            if (!Files.exists(dummyfilePath)) {

                                throw new FileNotFoundException();
                            }

                            File inputfile2 = dummyfilePath.toFile();
                            Document dummyDocument = builder.parse(inputfile2);
                            List<String> code2 = extractCode(dummyDocument);

                            List<String> common = new ArrayList<>(code1);
                            common.retainAll(code2);
                            int comCount = common.size();

                            List<String> extraInList1 = new ArrayList<>(code1);
                            extraInList1.removeAll(code2);
                            int extra = extraInList1.size();

                            List<String> extraincm2 = new ArrayList<>(code2);
                            extraincm2.removeAll(code1);
                            int missing = extraincm2.size();

                            System.out.println("\nCommon In CM1 and CM2 IS : " + common +" \nCount : "+comCount+ "\nExtra In CM1 is : " + extraInList1 +" \nCount : "+extra+ "\nExtra In CM2 is : " + extraincm2 +" \nCount : "+missing+ "\n\n\n");

                        } catch (SAXException | IOException e) {
                            System.out.println("Dummy file not found in CM2: " + e + "\n\n");
                        }

                    });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> extractCode(Document doc) {
        List<String> codeValues = new ArrayList<>();

        NodeList icdList = doc.getElementsByTagName("Icd10CmCode");

        for (int i = 0; i < icdList.getLength(); i++) {
            Node node = icdList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element icdElement = (Element) node;
                NodeList codeNodes = icdElement.getElementsByTagName("code");

                for (int j = 0; j < codeNodes.getLength(); j++) {
                    Element codeElements = (Element) codeNodes.item(j);
                    String code = codeElements.getAttribute("value");
                    codeValues.add(code);
                }

            }

        }
        return codeValues;
    }

}
