package HW.HW7.markup;

import java.util.List;

public class Emphasis extends MarkUp {

    public Emphasis(List<MarkUp> list) {
        fill_MarkDown(list);
        fill_HTML(list);
    }


    void addFront_Html(StringBuilder sb) {
        formats[HTML].append("<em>");
    }
    void addBack_Html(StringBuilder sb) {
        formats[HTML].append("</em>");
    }
    void add_MarkDown(StringBuilder sb) {
        formats[MARK_DOWN].append("*");
    }


}