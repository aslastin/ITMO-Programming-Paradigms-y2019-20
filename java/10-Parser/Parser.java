package HW.HW12.parser;

import HW.HW11.expression.TripleExpression;
import HW.HW13.parserExceptions.ParserException;

public interface Parser {
    TripleExpression parse(String expression) throws ParserException;
}