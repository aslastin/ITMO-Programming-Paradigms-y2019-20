package expression.generic.unaryOp;

import expression.generic.Expression;
import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public class Negate<T extends Number> extends UnaryOperation<T> {
    public Negate(Expression<T> arg) {
        super(arg, "-");
    }

    @Override
    public String toString() {
        if (stringForm == null) {
            stringForm = new StringBuilder();
            stringForm.append(operation).append(arg);
        }
        return stringForm.toString();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NEGATE;
    }

    @Override
    protected Type<T> calc(Type<T> a) throws ArithmeticExpressionException {
        return a.negate();
    }
}