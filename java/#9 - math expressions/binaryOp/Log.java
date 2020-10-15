package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW11.expression.Const;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ArithmeticParserExceptions.IllegalFunctionArgParserException;

public class Log extends BinaryOperation {
    public Log(CommonExpression firstArg, CommonExpression secondArg) {
        super(firstArg, secondArg);
        operationType = "log";
        operationHash = getHashByOp(operationType);
    }

    public Log(CommonExpression secondArg) {
        super(new Const(2), secondArg);
        operationType = "log";
        operationHash = getHashByOp(operationType);
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.LOG;
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
        int ans = 0;

        if (a <= 0 || a == 1) {
            throw new IllegalFunctionArgParserException("Incorrect logarithms body", operationType + '(' + a + ")(" + b + ')');
        }
        if (b <= 0) {
            throw new IllegalFunctionArgParserException("Incorrect logarithms argument", operationType + '(' + a + ")(" + b + ')');
        }

        while (b > 1 && b>= a) {
            b /= a;
            ans++;
        }

        return ans;
    }

    @Override
    protected double calc(double a, double b) {
        return 0;
    }
}
