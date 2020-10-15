package expression.generic.unaryOp;

import expression.generic.Expression;
import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.types.Type;

import java.util.HashMap;
import java.util.Map;

public abstract class UnaryOperation<T extends Number> implements Expression<T> {
    protected Expression<T> arg;
    protected String operation;
    private int operationHash;
    protected StringBuilder stringForm;
    private final int B = 29, C = 71 * 17 * 39;
    private final static Map<String, Integer> getHashByOp = new HashMap<>(Map.of(
            "-", 1,
            "count", 2
    ));

    public UnaryOperation(Expression<T> arg, String operation) {
        this.arg = arg;
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
            stringForm.append(operation).append('(').append(arg).append(')');
        }
        return stringForm.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        UnaryOperation<?> arg = (UnaryOperation<?>)obj;
        return operationHash == arg.operationHash && this.arg.equals(arg);
    }

    @Override
    public int hashCode() {
        return (B * arg.hashCode() + operationHash) % C;
    }

    @Override
    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) throws ArithmeticExpressionException {
        return calc(arg.evaluate(x, y, z));
    }

    protected abstract Type<T> calc(Type<T> a) throws ArithmeticExpressionException;
}