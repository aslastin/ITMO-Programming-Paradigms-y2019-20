package HW.HW7.markup;

import java.util.List;

public class Strikeout extends MarkUp {

    public Strikeout(List<MarkUp> list) {
        fill_MarkDown(list);
        fill_HTML(list);
    }


    void addFront_Html(StringBuilder sb) {
        formats[HTML].append("<s>");
    }
    void addBack_Html(StringBuilder sb) {
        formats[HTML].append("</s>");
    }
    void add_MarkDown(StringBuilder sb) {
        formats[MARK_DOWN].append("~");
    }


}
