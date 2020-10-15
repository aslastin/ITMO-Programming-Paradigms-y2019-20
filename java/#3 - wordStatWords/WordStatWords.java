package HW.HW4;

import HW.HW5.Scanner;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.lang.*;

public class WordStatWords {
    static Map<String, Integer> map = new TreeMap<>();

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(args[0]);
            String string;

            while (scanner.hasNextWord()) {
                string = scanner.nextWord().toLowerCase();
                pushMap(string);
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"));

            for (Map.Entry<String, Integer> pair : map.entrySet()) {
                writer.write(pair.getKey() + " " + pair.getValue() + '\n');
            }
            map.clear();

            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void pushMap(String string) {
        if (map.containsKey(string))  {
            map.put(string, map.get(string) + 1);
        } else {
            map.put(string, 1);
        }
    }
}