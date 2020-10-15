package HW.HW11.expression;

import HW.HW13.parserExceptions.ArithmeticExpressionException;

public interface Expression extends ToMiniString {
    int evaluate(int x) throws ArithmeticExpressionException;
}
