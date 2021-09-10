package HW.HW7.markup;

public class Text extends MarkUp {

    public Text(String string) {
       formats[HTML].append(string);
       formats[MARK_DOWN].append(string);
    }


    void addFront_Html(StringBuilder sb) {}
    void addBack_Html(StringBuilder sb) {}
    void add_MarkDown(StringBuilder sb) {}
}
