package expression.generic.types;

import expression.generic.parser.exceptions.ArithmeticExpressionException;

public interface Type<T extends Number> {
    T value();

    Type<T> negate() throws ArithmeticExpressionException;

    Type<T> multiply(Type<T> arg) throws ArithmeticExpressionException;

    Type<T> divide(Type<T> arg) throws ArithmeticExpressionException;

    Type<T> subtract(Type<T> arg) throws ArithmeticExpressionException;

    Type<T> add(Type<T> arg) throws ArithmeticExpressionException;

    Type<T> min(Type<T> arg);

    Type<T> max(Type<T> arg);

    Type<T> count();
}