package expression.generic;

import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;

public class Const<T extends Number> implements Expression<T> {
    protected Type<T> number;

    public Const(Type<T> value) {
        number = value;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.CONST;
    }

    @Override
    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Const<?> value = (Const<?>)obj;
        return number.equals(value.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
