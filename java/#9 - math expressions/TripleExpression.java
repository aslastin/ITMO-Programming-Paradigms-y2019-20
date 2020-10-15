package HW.HW11.expression;



import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ParserException;

public interface TripleExpression extends ToMiniString {
    int evaluate(int x, int y, int z) throws ArithmeticExpressionException;
}