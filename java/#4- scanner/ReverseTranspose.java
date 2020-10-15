package HW.HW5;

import java.io.IOException;
import java.util.ArrayList;

// /Users/aleksandrslastin/JavaHW/src
// javac done.HW5.ReverseTranspose.java
// java -jar FastReverseTransposeTest.jar

public class ReverseTranspose {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<Long> > list = new ArrayList<>();
        int i;
        long a;


        while (scanner.hasNext()) {
            i = 0;
            while (scanner.hasNextLongInLine()) {
                a = scanner.nextLong();
                if (i == list.size()) {
                    list.add(new ArrayList<Long>());
                }
                list.get(i++).add(a);
            }
        }

        for ( i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.print(list.get(i).get(j) + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
