package HW.HW12.parser;


public class StringSource implements ParserSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        if (pos < data.length()) {
            return data.charAt(pos++);
        }
        pos++;
        return '\0';
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public String getInput() {
        return data;
    }

    @Override
    public int getInputLength() {
        return data.length();
    }
}
