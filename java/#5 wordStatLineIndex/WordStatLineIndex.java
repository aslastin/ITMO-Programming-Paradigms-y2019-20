package HW.HW6;

import HW.HW5.Scanner;

import java.io.*;
import java.util.*;

// /Users/aleksandrslastin/JavaHW/src
// javac done.HW6.WordStatLineIndex.java
// java -jar WordStatLineIndexTest.jar

// /Users/aleksandrslastin/JavaHW/src/in.txt

public class WordStatLineIndex {


    static int x, y;

    public static void main(String[] args) {
        Map<String, PairIntSb> map = new TreeMap<>();

        try {
            String string;
            Scanner scanner = new Scanner(args[0]);

            while (scanner.hasNext()) {
                x++;
                y = 0;
                while (scanner.hasNextWordInLine()) {
                    string = scanner.nextWord().toLowerCase();
                    y++;
                    pushMapOfStrPair(string, map);
                }
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), "utf8"));

            for (Map.Entry<String, PairIntSb> pair : map.entrySet()) {
                writer.write(pair.getKey() + " " + pair.getValue().getCount() + pair.getValue().getSb().toString() + '\n');
            }
            map.clear();
            x = 0;
            y = 0;

            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }


    public static void pushMapOfStrPair (String string, Map<String, PairIntSb> map) {
        if (map.containsKey(string))  {
            map.get(string).increamentCount();
            map.get(string).append(" " + x + ":" + y);
        } else {
            map.put(string, new PairIntSb());
            map.get(string).append(" " + x + ":" + y);
        }
    }
}