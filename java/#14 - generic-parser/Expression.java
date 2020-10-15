package expression.generic;

import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public interface Expression<T extends Number> extends ToMiniString {
    Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) throws ArithmeticExpressionException;
    TokenType getTokenType();
}
