package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

public class Digits extends UnaryOperation {
    public Digits(CommonExpression arg) {
        super(arg);
        operationType = "digits";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.REVERSE;
    }

    @Override
    protected int calc(int a) throws ArithmeticExpressionException {
        return getDigits(a + "");
    }

    @Override
    protected double calc(double a) {
        return getDigits(a + "");
    }

    private int getDigits(String string) {
        int count = 0;

        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                count += (int)string.charAt(i) - 48;
            }
        }

        return count;
    }
}
