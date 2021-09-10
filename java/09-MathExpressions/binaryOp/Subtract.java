package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

public class Subtract extends BinaryOperation {
    public Subtract(CommonExpression firstArg, CommonExpression secondArg) {
        super(firstArg, secondArg);
        operationType = "-";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.SUB;
    }

    @Override
    protected int calc(int a, int b) throws ArithmeticExpressionException {
        return a - b;
    }

    @Override
    protected double calc(double a, double b) {
        return a - b;
    }
}
