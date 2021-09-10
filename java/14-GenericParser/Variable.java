package expression.generic;

import expression.generic.parser.token.TokenType;
import expression.generic.types.Type;


public class Variable<T extends Number> implements Expression<T> {
    private String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.VAR;
    }

    @Override
    public Type<T> evaluate(Type<T> x, Type<T> y, Type<T> z) {
        switch (variable) {
            case "x" : {
                return x;
            }
            case "y" : {
                return y;
            }
            case "z" : {
                return z;
            }
            default: {
                throw new IllegalArgumentException("Incorrect variable " + variable);
            }
        }
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return variable.equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }
}
