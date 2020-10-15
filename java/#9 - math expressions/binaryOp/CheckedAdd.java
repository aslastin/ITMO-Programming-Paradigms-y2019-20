package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.OverflowParserException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.UnderflowParserException;

public class CheckedAdd extends Add {
    public CheckedAdd(CommonExpression firstArg, CommonExpression secondArg) {
        super(firstArg, secondArg);
    }


    @Override
    protected int calc(int a, int b) throws OverflowParserException, UnderflowParserException  {
        return safeAdd(a, b);
    }

    public static int safeAdd(int a, int b) throws OverflowParserException, UnderflowParserException  {
        if (a == 0 || b  == 0) {
            return a + b ;
        }
        if (a > 0 && b  > 0 && a > Integer.MAX_VALUE - b ) {
            throw new OverflowParserException(a + " + " + b );
        }
        if (a < 0 && b  < 0 && a < Integer.MIN_VALUE - b ) {
            throw new UnderflowParserException(a + " + " + b );
        }
        return a + b;
    }
}
