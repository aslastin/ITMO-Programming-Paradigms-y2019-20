package HW.HW7.markup;

import java.util.*;

public abstract class MarkUp {
    protected final static int MARK_DOWN = 0;
    protected final static int HTML = 1;

    protected final static int COUNT = 2;

    protected StringBuilder[] formats;

    public MarkUp() {
        formats = new StringBuilder[COUNT];
        for (int i = 0; i < COUNT; i++) {
            formats[i] = new StringBuilder();
        }
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(formats[MARK_DOWN]);
    }

    public void toHtml(StringBuilder sb) {
        sb.append(formats[HTML]);
    }

    void fill_HTML(List<MarkUp> list) {
        addFront_Html(formats[HTML]);
        for (MarkUp val : list) {
            formats[HTML].append(val.formats[HTML]);
        }
        addBack_Html(formats[HTML]);
    }

    void fill_MarkDown(List<MarkUp> list) {
        add_MarkDown(formats[MARK_DOWN]);
        for (MarkUp val : list) {
            formats[MARK_DOWN].append(val.formats[MARK_DOWN]);
        }
        add_MarkDown(formats[MARK_DOWN]);
    }


    abstract void addFront_Html(StringBuilder sb);
    abstract void addBack_Html(StringBuilder sb);
    abstract void add_MarkDown(StringBuilder sb);


}
