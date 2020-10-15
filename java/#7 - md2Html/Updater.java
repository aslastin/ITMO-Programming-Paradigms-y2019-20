package HW.HW9.md2html;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Updater {
    private StringBuilder sb;
    private Stack<String>  formatsStack;
    private static Map<String, String> mapFormats = new HashMap<>(Map.of(
            "*", "em>",
            "_", "em>",
            "**", "strong>",
            "__", "strong>",
            "`", "code>",
            "--", "s>",
            "++", "u>"
    ));


    private boolean token, isFormat, isParagraph, isClear;
    private int countHeader;



    private String checkFormat(char[] cbuff) {
        String string = new String(cbuff);

        if (mapFormats.containsKey(string)) {
            return string;
        }

        if (mapFormats.containsKey(string = string.substring(0, 1))) {
            return string;
        }
        return "";
    }

    private String special (char[] buff) {
        if (buff[0] == '&') return "&amp;";
        if (buff[0] == '<') return "&lt;";
        if (buff[0] == '>') return "&gt;";
        if (buff[0] == '\\') {
            token = true;
            return "\\";
        }
        return "";
    }

    private int addStartTag(final String string, int index) {
        if (countHeader > 0) {
            if (index >= string.length()) {
                sb.append("<p>");
                isParagraph = true;
                return 0;
            } else {
                if (string.charAt(index) == ' ') {
                    sb.append("<h").append(countHeader).append(">");
                    return index + 1;
                } else {
                    sb.append("<p>");
                    isParagraph = true;
                    return 0;
                }
            }
        } else {
            sb.append("<p>");
            isParagraph = true;
            return index;
        }
    }


    public void updateToHtml(final String string) {
        char[] cbuff = new char[2];
        int index = 0;
        String str;

        if (!isClear) clear();

        while (index < string.length() && string.charAt(index) == '#') {
            countHeader++;
            index++;
        }

        index = addStartTag(string, index);


        cbuff[1] = string.charAt(index++);


            for (int i = index; i < string.length(); i++) {
                cbuff[0] = cbuff[1];
                cbuff[1] = string.charAt(i);

                if (isFormat || token) {
                    if (token) {
                        sb.append(cbuff[0]);
                    }
                    isFormat = token = false;
                    continue;
                }

                if ((str = special(cbuff)).length() > 0) {
                    if (!str.equals("\\")) {
                        sb.append(str);
                    }
                } else {
                    if ((str = checkFormat(cbuff)).length() > 0) {

                        if (str.length() == 2) {
                            if (formatsStack.empty() || (!formatsStack.empty() && !formatsStack.peek().equals(str))) {
                                formatsStack.push(str);
                                sb.append("<").append(mapFormats.get(str));
                            } else {
                                formatsStack.pop();
                                sb.append("</").append(mapFormats.get(str));
                            }

                            isFormat = true;
                        } else {
                            if (formatsStack.empty() || (!formatsStack.empty() && !formatsStack.peek().equals(str))) {
                                if (cbuff[1] != ' ' && cbuff[1] != '\n') {
                                    formatsStack.push(str);
                                    sb.append("<").append(mapFormats.get(str));
                                } else {
                                    sb.append(str);
                                }
                            } else {
                                formatsStack.pop();
                                sb.append("</").append(mapFormats.get(str));
                            }
                        }
                    } else {
                        sb.append(cbuff[0]);
                    }
                }
            }

        if (!formatsStack.empty()) {
            str = "" + cbuff[1];
            sb.append("</").append(mapFormats.get(str));
        }
        else {
            if (!isFormat) {
                sb.append(cbuff[1]);
            }
        }

        if (isParagraph) {
            sb.append("</p>").append('\n');
        } else {
            sb.append("</h").append(countHeader).append(">").append('\n');
        }
        isClear = false;
    }

    public Updater() {
        sb = new StringBuilder();
        formatsStack = new Stack<>();
        countHeader = 0;
        isClear = true;
    }

    public String getString() {
        return sb.toString();
    }

    public void clear() {
        sb.delete(0, sb.length());
        formatsStack.clear();
        token = isFormat = isParagraph = false;
        countHeader = 0;
        isClear = true;
    }
}
