package HW.HW11.expression.unaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

import java.util.HashMap;
import java.util.Map;

public abstract class UnaryOperation implements CommonExpression {
    protected CommonExpression arg;
    protected String operationType;
    protected int operationHash;
    protected StringBuilder stringFormat;
    private final int B = 29, C = 71 * 17 * 39;
    private final static Map<String, Integer> getHashByOp = new HashMap<>(Map.of(
            "-", 1,
            "abs", 2,
            "square",3,
            "log2", 4,
            "pow2", 5,
            "reverse", 6,
            "digits", 7
    ));


    public UnaryOperation(CommonExpression arg) {
        this.arg = arg;
    }

    public int getHashByOp(String op) {
        if (getHashByOp.containsKey(op)) {
            return getHashByOp.get(op);
        }
        throw new IllegalStateException("No operation found!!!");
    }

    @Override
    public String toString() {
        if (stringFormat == null) {
            stringFormat = new StringBuilder();
            stringFormat.append(operationType).append('(').append(arg).append(')');
        }
        return stringFormat.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        UnaryOperation arg = (UnaryOperation)obj;

        return operationHash == arg.operationHash && this.arg.equals(arg);
    }

    @Override
    public int hashCode() {
        return (B * arg.hashCode() + operationHash) % C;
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticExpressionException {
        return calc(arg.evaluate(x, y, z));
    }

    @Override
    public double evaluate(double x) {
        return calc(arg.evaluate(x));
    }

    @Override
    public int evaluate(int x) throws ArithmeticExpressionException  {
        return calc(arg.evaluate(x));
    }

    protected abstract int calc(int a) throws ArithmeticExpressionException;

    protected abstract double calc(double a);

}
