package HW.HW11.expression.binaryOp;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.Lexeme;
import HW.HW13.parserExceptions.ArithmeticExpressionException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BinaryOperation implements CommonExpression {
    protected CommonExpression firstArg, secondArg;
    protected String operationType;
    protected int operationHash;
    protected StringBuilder stringFormat, miniStringFormat;
    private final static int A = 29, B = 37, C = 71 * 31 * 39;
    private final static Map<String, Integer> getHashByOp = new HashMap<>(Map.of(
            "+", 1,
            "-", 2,
            "*", 3,
            "/", 4,
            ">>", 5,
            "<<", 6,
            "log", 7,
            "pow", 8
    ));

    private final static Set<Lexeme> associative = new HashSet<>(Set.of(
            Lexeme.ADD, Lexeme.MUL
    ));


    public BinaryOperation(CommonExpression firstArg, CommonExpression secondArg) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
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
            stringFormat.append("(").append(firstArg).append(" ").append(operationType)
                        .append(" ").append(secondArg).append(")");
        }
        return stringFormat.toString();
    }

    @Override
    public String toMiniString() {
        if (miniStringFormat == null) {
            miniStringFormat = new StringBuilder();
            if (checkForBracket(firstArg) || firstArg.getLexeme().priority() == this.getLexeme().priority()) {
                miniStringFormat.append(firstArg.toMiniString());
            } else {
                miniStringFormat.append('(').append(firstArg.toMiniString()).append(')');
            }
            miniStringFormat.append(' ').append(operationType).append(' ');
            if (checkForBracket(secondArg)) {
                miniStringFormat.append(secondArg.toMiniString());
            } else {
                miniStringFormat.append('(').append(secondArg.toMiniString()).append(')');
            }
        }
        return miniStringFormat.toString();
    }

    private boolean checkForBracket(CommonExpression arg) {
        return arg.getLexeme() == Lexeme.VAR || arg.getLexeme() == Lexeme.CONST || arg.getLexeme().priority() > this.getLexeme().priority()
                || arg.getLexeme() == this.getLexeme() && associative.contains(this.getLexeme());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        BinaryOperation arg = (BinaryOperation)obj;

        return operationHash == arg.operationHash && firstArg.equals(arg.firstArg)
                && secondArg.equals(arg.secondArg);
    }

    @Override
    public int hashCode() {
            return (A * firstArg.hashCode() + B * secondArg.hashCode() + operationHash) % C;
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticExpressionException {
        return calc(firstArg.evaluate(x, y, z), secondArg.evaluate(x, y, z));
    }

    @Override
    public double evaluate(double x) {
        return calc(firstArg.evaluate(x), secondArg.evaluate(x));
    }

    @Override
    public int evaluate(int x) throws ArithmeticExpressionException  {
        return calc(firstArg.evaluate(x), secondArg.evaluate(x));
    }

    protected abstract int calc(int a, int b) throws ArithmeticExpressionException;

    protected abstract double calc(double a, double b);
}
