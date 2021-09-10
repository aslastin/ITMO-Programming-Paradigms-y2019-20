package HW.HW5;

import java.lang.Character;
import java.io.*;


public class Scanner {
    private BufferedReader in;
    private int indicator;
    private boolean checkMinus;
    private StringBuilder sb = new StringBuilder();

    public Scanner (InputStream inputStream) throws IOException {
        in = new BufferedReader(new InputStreamReader(inputStream));
        indicator = in.read();
    }

    public Scanner (File file) throws IOException {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        indicator = in.read();
    }

    public Scanner (String fileName) throws IOException {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        indicator = in.read();
    }

    //Helpful methods

    private boolean isWord() {
        return (Character.isLetter(indicator) ||
                Character.DASH_PUNCTUATION == Character.getType(indicator) || indicator == '\'');
    }

    //hasNext

    public boolean hasNext() throws IOException {
        return indicator != -1;
    }

    public boolean hasNextWord() throws  IOException {
            while (!isWord() && indicator != -1) {
                indicator = in.read();
            }
        return indicator != -1;
    }

    public boolean hasNextLong() throws IOException {
            while (!Character.isDigit(indicator) && indicator != -1) {
                if (indicator == '-') {
                    checkMinus = true;
                    indicator = in.read();
                    if (Character.isDigit(indicator)) {
                        return true;
                    } else {
                        checkMinus = false;
                    }
                } else {
                    indicator = in.read();
                }
            }
        return indicator != -1;
    }

    public boolean hasNextLongInLine() throws IOException {
            while (!Character.isDigit(indicator) && indicator != '\n' && indicator != -1) {
                if (indicator == '-') {
                    checkMinus = true;
                    indicator = in.read();
                    if (Character.isDigit(indicator)) {
                        return true;
                    } else {
                        checkMinus = false;
                    }
                } else {
                    indicator = in.read();
                }
            }

            if (indicator == '\n') {
                indicator = in.read();
                return false;
            }
        return (indicator != -1);
    }

    public boolean hasNextWordInLine() throws IOException {
            while (hasNext() && indicator != '\n' && !isWord()) {
                indicator = in.read();
            }
        if (indicator == '\n') {
            indicator = in.read();
            return false;
        }
        return indicator != -1;
    }

    //next

    public char next() throws IOException {
        char symbol = (char)indicator;
        indicator = in.read();
        return symbol;
    }

    public String nextLine() throws IOException {
        if (sb.length() > 0) sb.delete(0, sb.length());
        if (indicator != '\n' && indicator != -1) {
            sb.append((char)indicator);
            String str = in.readLine();
            indicator = in.read();
            if (str == null) return sb.toString();
            sb.append(str);
            return sb.toString();
        }
        indicator = in.read();
        return "";
    }

    public Long nextLong() throws IOException {
        if (sb.length() > 0) sb.delete(0, sb.length());

        if (indicator == -1) throw new IOException();

        if (indicator == '\n') {
            hasNextLong();
        }

        if (checkMinus) {
            sb.append('-');
            checkMinus = false;
        }
        sb.append((char)indicator);
        while (Character.isDigit(indicator = in.read())) {
            sb.append((char)indicator);
        }
        return Long.parseLong(sb.toString());
    }


    public String nextWord() throws IOException {
        if (sb.length() > 0) sb.delete(0, sb.length());

        if (indicator == -1) throw new IOException();
        if (indicator == '\n') {
            hasNextWord();
        }
        while (isWord()) {
            sb.append((char)indicator);
            indicator = in.read();
        }
        return sb.toString();
    }

    public void close() throws IOException {
        in.close();
    }
}