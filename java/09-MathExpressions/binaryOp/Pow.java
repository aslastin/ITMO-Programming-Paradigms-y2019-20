package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW11.expression.Const;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.IllegalFunctionArgParserException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.OverflowParserException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.UnderflowParserException;


public class Pow extends CheckedMultiply {

    public Pow(CommonExpression firstArg, CommonExpression secondArg) {
        super(firstArg, secondArg);
        operationType = "pow";
        operationHash = getHashByOp(operationType);
    }

    public Pow(CommonExpression secondArg) {
        super(new Const(2), secondArg);
        operationType = "pow";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.POW;
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();

            stringFormat.append(operationType).append('(').append(firstArg).append(')')
                    .append('(').append(secondArg).append(')');
        }
        return stringFormat.toString();
    }


    @Override
    protected int calc(int a, int b) throws ArithmeticExpressionException {
        int res = 1;
        if (b < 0) {
            throw new IllegalFunctionArgParserException("Negative pow argument", operationType + '(' + a + ")(" + b+ ')');
        }
        if (a == 0 && b== 0) {
            throw new IllegalFunctionArgParserException("0^0 incorrect");
        }
        if (b == 0) {
            return 1;
        }
        while (b > 1) {
            if (b % 2 == 1) {
                res = safeMultiply(res, a);
            }

            a = safeMultiply(a, a);
            b /= 2;
        }

        return safeMultiply(res, a);
    }

    @Override
    protected double calc(double a, double b) {
        return 0;
    }
}
