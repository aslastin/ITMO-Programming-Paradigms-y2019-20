package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

import java.util.StringJoiner;

public class Reverse extends UnaryOperation {

    public Reverse(CommonExpression arg) {
        super(arg);
        operationType = "reverse";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.REVERSE;
    }

    @Override
    protected int calc(int a) throws ArithmeticExpressionException {
        StringBuilder num = createStringBuilderWithAbsNumber(a);
        long res = a < 0 ? Long.parseLong('-' + num.reverse().toString()) : Long.parseLong(num.reverse().toString());
        return (int)res;
    }

    @Override
    protected double calc(double a) {
        return 0;
    }

    private StringBuilder createStringBuilderWithAbsNumber(int a) {
        StringBuilder num = new StringBuilder(a + "");
        if (a < 0) {
            num.deleteCharAt(0);
        }
        return num;
    }
}
