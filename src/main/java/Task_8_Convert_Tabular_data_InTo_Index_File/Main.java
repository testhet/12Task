package Task_8_Convert_Tabular_data_InTo_Index_File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

        static class Noode {
            String code;
            String name;
            List<Noode> children;

            public Noode(String code, String name) {
                this.code = code;
                this.name = name;
                this.children = new ArrayList<>();
            }
        }

        public static void main(String[] args) {
            File inputFile = new File("/home/hetgoti/Downloads/Problem_Input/8/ReadMe.txt");
        // add null to root
            Noode root = new Noode("", "");
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t");
                    addNodeToTree(root, parts);
                }
                printNode(root, -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // add to tree.
        private static void addNodeToTree(Noode root, String[] parts) {
            Noode currentNode = root;
            for (int i = 1; i < parts.length - 1; i++) {
                String elements = parts[i];
                Noode matchingNode = findMatchingNode(currentNode.children, elements);
                if (matchingNode == null) {
                    matchingNode = new Noode("", elements);
                    currentNode.children.add(matchingNode);
                }
                currentNode = matchingNode;
            }
            currentNode.children.add(new Noode(parts[0], parts[parts.length - 1]));
        }

        private static Noode findMatchingNode(List<Noode> nodes, String name) {
            for (Noode node : nodes) {
                if (node.name.equals(name)) {
                    return node;
                }
            }
            return null;
        }

        // print nodes.
        private static void printNode(Noode node, int depth) {
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                indent.append("\t");
            }
            System.out.println(indent + node.name + (node.code.isEmpty() ? "" : " ~ " + node.code));
            for (Noode child : node.children) {
                printNode(child, depth + 1);
            }
        }
    }