package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

public class Square extends UnaryOperation {
    public Square(CommonExpression arg) {
        super(arg);
        operationType = "square";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.SQUARE;
    }

    @Override
    protected int calc(int a) throws ArithmeticExpressionException {
        return a * a;
    }

    @Override
    protected double calc(double a) {
        return a * a;
    }
}
