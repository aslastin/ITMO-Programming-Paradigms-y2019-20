package expression.generic.binaryOp;

import expression.generic.Expression;
import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BinaryOperation<T extends Number> implements Expression<T> {
    protected Expression<T> firstArg, secondArg;
    protected String operation;
    private int operationHash;
    protected StringBuilder stringForm;
    private final static int A = 29, B = 37, C = 71 * 31 * 39;
    private final static Map<String, Integer> getHashByOp = new HashMap<>(Map.of(
            "+", 1,
            "-", 2,
            "*", 3,
            "/", 4,
            "min", 5,
            "max", 6
    ));

    private final static Set<TokenType> associative = new HashSet<>(Set.of(
            TokenType.ADD, TokenType.MUL
    ));

    public BinaryOperation(Expression<T> firstArg, Expression<T> secondArg, String operation) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
        if (getHashByOp.containsKey(operation)) {
            operationHash = getHashByOp.get(operation);
        } else {
            throw new IllegalStateException("Nonexistent operation");
        }
        this.operation = operation;
    }

    @Override
    public String toString() {
        if (stringForm == null) {
            stringForm = new StringBuilder();
            if (checkForBracket(firstArg) || firstArg.getTokenType().priority() == this.getTokenType().priority()) {
                stringForm.append(firstArg.toMiniString());
            } else {
                stringForm.append('(').append(firstArg.toMiniString()).append(')');
            }
            stringForm.append(' ').append(operation).append(' ');
            if (checkForBracket(secondArg)) {
                stringForm.append(secondArg.toMiniString());
            } else {
                stringForm.append('(').append(secondArg.toMiniString()).append(')');
            }
        }
        return stringForm.toString();
    }

    private boolean checkForBracket(Expression<T> arg) {
        return arg.getTokenType() == TokenType.VAR || arg.getTokenType() == TokenType.CONST || arg.getTokenType().priority() < this.getTokenType().priority()
                || arg.getTokenType() == this.getTokenType() && associative.contains(this.getTokenType());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        BinaryOperation<?> arg = (BinaryOperation<?>)obj;
        return operationHash == arg.operationHash && firstArg.equals(arg.firstArg)
                && secondArg.equals(arg.secondArg);
    }

    @Override
    public int hashCode() {
        return (A * firstArg.hashCode() + B * secondArg.hashCode() + operationHash) % C;
    }

    @Override
    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) throws ArithmeticExpressionException {
        return calc(firstArg.evaluate(x, y, z), secondArg.evaluate(x, y, z));
    }

    protected abstract Type<T> calc(Type<T> a, Type<T> b) throws ArithmeticExpressionException;
}
