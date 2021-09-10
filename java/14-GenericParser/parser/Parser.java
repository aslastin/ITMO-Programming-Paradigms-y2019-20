package expression.generic.parser;

import expression.generic.Expression;
import expression.generic.parser.exceptions.ParserException;


public interface Parser<T extends Number> {
    Expression<T> parse(String expression) throws ParserException;
}
