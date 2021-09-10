package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;


public class Abs extends UnaryOperation {
    public Abs(CommonExpression arg) {
        super(arg);
        operationType = "abs";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.ABS;
    }

    @Override
    protected int calc(int a) throws ArithmeticExpressionException {
        return Math.abs(a);
    }

    @Override
    protected double calc(double a) {
        return Math.abs(a);
    }
}
