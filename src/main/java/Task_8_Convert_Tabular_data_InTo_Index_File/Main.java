package Task_8_Convert_Tabular_data_InTo_Index_File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

        static class Node {
            String code;
            String name;
            List<Node> children;

            public Node(String code, String name) {
                this.code = code;
                this.name = name;
                this.children = new ArrayList<>();
            }
        }

        public static void main(String[] args) {
            File inputFile = new File("/home/hetgoti/Downloads/Problem_Input/8/InputFile");
            Node root = new Node("", "");
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t");
                    addNode(root, parts);
                }
                printNode(root, -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void addNode(Node root, String[] parts) {
            Node currentNode = root;
            for (int i = 1; i < parts.length - 1; i++) {
                String elements = parts[i];
                Node matchingNode = matchNodes(currentNode.children, elements);
                if (matchingNode == null) {
                    matchingNode = new Node("", elements);
                    currentNode.children.add(matchingNode);
                }
                currentNode = matchingNode;
            }
            currentNode.children.add(new Node(parts[0], parts[parts.length - 1]));
        }

        private static Node matchNodes(List<Node> nodes, String name) {
            for (Node node : nodes) {
                if (node.name.equals(name)) {
                    return node;
                }
            }
            return null;
        }

        private static void printNode(Node node, int depth) {
            StringBuilder tab = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tab.append("\t");
            }
            System.out.println(tab + node.name + (node.code.isEmpty() ? "" : " ~ " + node.code));
            for (Node child : node.children) {
                printNode(child, depth + 1);
            }
        }
    }