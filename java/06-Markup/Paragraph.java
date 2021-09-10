package HW.HW7.markup;

import java.util.List;

public class Paragraph extends MarkUp {

    public Paragraph(List<MarkUp> list) {
        fill_MarkDown(list);
        fill_HTML(list);
    }

    void addFront_Html(StringBuilder sb) {}
    void addBack_Html(StringBuilder sb) {}
    void add_MarkDown(StringBuilder sb) {}

}

