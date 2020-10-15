package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.IllegalFunctionArgParserException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.OverflowParserException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.UnderflowParserException;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(CommonExpression firstArg, CommonExpression bArg) {
        super(firstArg, bArg);
    }

    protected int calc(int a, int b) throws OverflowParserException, UnderflowParserException, ArithmeticExpressionException {
        return safeMultiply(a, b);
    }

    public static int safeMultiply(int a, int b) throws OverflowParserException, UnderflowParserException  {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (a > 0) {
            if (b > 0) {
                if (a > Integer.MAX_VALUE / b) {
                    throw new OverflowParserException(a + " * " + b);
                }
            } else {
                if (b < Integer.MIN_VALUE / a) {
                    throw new UnderflowParserException(a + " * " + b);
                }
            }
        } else {
            if (b > 0) {
                if (a < Integer.MIN_VALUE / b) {
                    throw new UnderflowParserException(a + " * " + b);
                }
            } else {
                if (a < Integer.MAX_VALUE / b) {
                    throw new OverflowParserException(a + " * " + b);
                }
            }
        }
        return a * b;
    }


}
