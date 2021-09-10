package HW.HW7.markup;

import java.util.List;

public class Strong extends MarkUp {

    public Strong(List<MarkUp> list) {
        fill_MarkDown(list);
        fill_HTML(list);
    }

    void addFront_Html(StringBuilder sb) {
        formats[HTML].append("<strong>");
    }
    void addBack_Html(StringBuilder sb) {
        formats[HTML].append("</strong>");
    }
    void add_MarkDown(StringBuilder sb) {
        formats[MARK_DOWN].append("__");
    }

}
