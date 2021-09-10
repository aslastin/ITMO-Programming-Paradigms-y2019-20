package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

public class Negate extends UnaryOperation {
    public Negate(CommonExpression arg) {
        super(arg);
        operationType = "-";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();
            stringFormat.append(operationType).append(arg);
        }
        return stringFormat.toString();
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.NEGATE;
    }

    @Override
    protected int calc(int a) throws ArithmeticExpressionException {
        return -a;
    }

    @Override
    protected double calc(double a) {
        return -a;
    }
}
