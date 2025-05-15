package Task_7_Convert_InxedFile_Into_tabular_Formate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static Deque<String> queue = new ArrayDeque<>();
    static String code = "";
    public static void main(String[] args) {

        String str;
        int prevTab;
        int currTab = 0;
        try {
            FileReader fr = new FileReader("/home/hetgoti/Downloads/Problem_Input/7/InputFile");
            BufferedReader br = new BufferedReader(fr);
            while ((str = br.readLine()) != null) {
                str = str.split("@@")[0];
                if (!str.contains("\t")) {
                    queue.clear();
                    currTab = 0;
                    queue.add(str);
                } else {
                    prevTab = currTab;
                    currTab = countWords('\t', str);
                    if (prevTab >= currTab) {
                        delQueue((prevTab - currTab) + 1);
                    }
                    if (str.contains("~")) {
                        String[] temp = str.split("~");
                        code = temp[1].trim();
                        temp[0] = temp[0].replace("\t", "");
                        queue.add(temp[0]);
                        print(queue, code);
                    } else {
                        queue.add(str.trim());
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void print(Deque<String> l, String code) {
        StringBuilder builder = new StringBuilder();
        for (String s : l) {
            builder.append(s).append("\t");
        }
        System.out.println(code + ":" + builder);
    }

    public static int countWords(char character, String splitLine) {
        int counter = 0;
        for (int i = 0; i < splitLine.length(); i++) {
            if ((splitLine.charAt(i)) == character) {
                counter++;
            } else {
                break;
            }
        }
        return counter;
    }

    private static void delQueue(int delCounter) {
        while (delCounter != 0) {
            queue.pollLast();
            delCounter--;
        }
    }
}