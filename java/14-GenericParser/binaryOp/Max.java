package expression.generic.binaryOp;

import expression.generic.Expression;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public class Max<T extends Number> extends BinaryOperation<T> {
    public Max(Expression<T> first, Expression<T> second) {
        super(first, second, "max");
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.MAX;
    }

    @Override
    protected Type<T> calc(Type<T> a, Type<T>  b) {
        return a.max(b);
    }
}