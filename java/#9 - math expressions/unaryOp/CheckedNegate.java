package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.OverflowParserException;

public class CheckedNegate extends Negate {
    public CheckedNegate(CommonExpression arg) {
        super(arg);
    }

    @Override
    protected int calc(int a) throws OverflowParserException {
        return safeNegate(a);
    }

    public static int safeNegate(int a) throws OverflowParserException {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowParserException("- " + a);
        }

        return -a;
    }
}
