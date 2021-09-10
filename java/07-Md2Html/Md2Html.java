package HW.HW9.md2html;

import HW.HW5.Scanner;

import java.io.*;


//

public class Md2Html {

    public static void main(String[] args)  {

        StringBuilder sb = new StringBuilder();
        Updater updater = new Updater();
        String string = "";

        try {
            Scanner sc = new Scanner(args[0]);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));

            while (sc.hasNext()) {
                while (string.equals("") && sc.hasNext()) {
                    string = sc.nextLine();
                }

                sb.append(string).append("\n");
                while (!(string = sc.nextLine()).equals("")) {
                    sb.append(string).append('\n');
                }

                if (sb.charAt(sb.length() -1) == '\n')  {
                    sb.deleteCharAt(sb.length() - 1);
                }

                updater.updateToHtml(sb.toString());

                sb.delete(0, sb.length());

                out.write(updater.getString());

                updater.clear();
            }
            sc.close();
            out.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}