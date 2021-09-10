package expression.generic.binaryOp;

import expression.generic.Expression;
import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public class Subtract<T extends Number> extends BinaryOperation<T> {
    public Subtract(Expression<T> firstArg, Expression<T> secondArg) {
        super(firstArg, secondArg, "-");
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.SUB;
    }

    @Override
    protected Type<T> calc(Type<T> a, Type<T>  b) throws ArithmeticExpressionException {
        return a.subtract(b);
    }
}
