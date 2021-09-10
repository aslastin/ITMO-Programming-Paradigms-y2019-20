package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.DBZParserException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.OverflowParserException;

public class CheckedDivide extends Divide {
    public CheckedDivide(CommonExpression firstArg, CommonExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    protected int calc(int a, int b) throws DBZParserException, OverflowParserException {
        return safeDivide(a, b);
    }

    public static int safeDivide(int a, int b) throws DBZParserException, OverflowParserException {
        if (b == 0) {
            throw new DBZParserException(a + " / " + b);
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowParserException(a + " / " + b);
        }
        return a / b;
    }
}
