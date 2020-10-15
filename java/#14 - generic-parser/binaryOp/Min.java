package expression.generic.binaryOp;

import expression.generic.Expression;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public class Min<T extends Number> extends BinaryOperation<T> {
    public Min(Expression<T> first, Expression<T> second) {
        super(first, second, "min");
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.MIN;
    }

    @Override
    protected Type<T> calc(Type<T> a, Type<T>  b) {
        return a.min(b);
    }

}
