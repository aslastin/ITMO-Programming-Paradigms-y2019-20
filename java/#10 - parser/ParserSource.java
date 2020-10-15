package HW.HW12.parser;



public interface ParserSource  {
    boolean hasNext();
    char next();
    int getPosition();
    String getInput();
    int getInputLength();
}
