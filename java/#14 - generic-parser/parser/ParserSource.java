package expression.generic.parser;



public interface ParserSource  {
    boolean hasNext();
    char next();
    int getPosition();
    String getInput();
}
