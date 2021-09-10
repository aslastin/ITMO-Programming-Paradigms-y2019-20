package expression.generic.unaryOp;

import expression.generic.Expression;
import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public class Count<T extends Number> extends UnaryOperation<T> {
    public Count(Expression<T> arg) {
        super(arg, "count");
    }

    @Override
    public String toString() {
        if (stringForm == null) {
            stringForm = new StringBuilder();
            stringForm.append(operation).append(' ').append(arg);
        }
        return stringForm.toString();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.COUNT;
    }

    @Override
    protected Type<T> calc(Type<T> a) {
        return a.count();
    }
}