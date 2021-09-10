package HW.HW3;

// in this realisation use my own Scanner
import HW4.Scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseEven {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb =  new StringBuilder(), ans = new StringBuilder();
        long number;

        while (scanner.hasNext()) {
            while (scanner.hasNextLongInLine()) {
                number = scanner.nextLong();
                sb.append(number);
                if (number % 2 == 0) {
                    ans.append(sb.reverse() + " ");
                }
                sb.delete(0, sb.length());
            }
            list.add(ans.reverse().toString());
            ans.delete(0, ans.length());
        }
        Collections.reverse(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        scanner.close();
    }
}