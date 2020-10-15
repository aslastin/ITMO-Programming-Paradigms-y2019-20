package expression.generic.types;


import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.typeParser.TypeParser;

public abstract class AbstractType<T extends Number> implements Type<T> {
    protected T value;

    public AbstractType(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public abstract Type<T> wrap(T arg);

    @Override
    public T value() {
        return value;
    }

    @Override
    public Type<T> negate() throws ArithmeticExpressionException {
        return wrap(applyNegate());
    }

    @Override
    public Type<T> multiply(Type<T> arg) throws ArithmeticExpressionException {
        return wrap(applyMultiply(arg.value()));
    }

    @Override
    public Type<T> divide(Type<T> arg) throws ArithmeticExpressionException {
        return wrap(applyDivide(arg.value()));
    }

    @Override
    public Type<T> subtract(Type<T> arg) throws ArithmeticExpressionException {
        return wrap(applySubtract(arg.value()));
    }

    @Override
    public Type<T> add(Type<T> arg) throws ArithmeticExpressionException {
        return wrap(applyAdd(arg.value()));
    }

    @Override
    public Type<T> min(Type<T> arg) {
        return wrap(applyMin(arg.value()));
    }

    @Override
    public Type<T> max(Type<T> arg) {
        return wrap(applyMax(arg.value()));
    }

    @Override
    public Type<T> count() {
       return wrap(applyCount());
    }

    protected abstract T applyNegate() throws ArithmeticExpressionException;

    protected abstract T applyMultiply(T arg) throws ArithmeticExpressionException;

    protected abstract T applyDivide(T arg) throws ArithmeticExpressionException;

    protected abstract T applySubtract(T arg) throws ArithmeticExpressionException;

    protected abstract T applyAdd(T arg) throws ArithmeticExpressionException;

    protected abstract T applyMin(T arg);

    protected abstract T applyMax(T arg);

    protected abstract T applyCount();
}
