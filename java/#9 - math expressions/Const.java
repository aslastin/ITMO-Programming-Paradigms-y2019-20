package HW.HW11.expression;

import HW.HW12.parser.Lexeme;

public class Const implements CommonExpression {
    private Number number;

    public Const(Number value) {
        number = value;
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.CONST;
    }

    @Override
    public int evaluate(int x) {
        return number.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return number.intValue();
    }

    @Override
    public double evaluate(double x) {
        return number.doubleValue();
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

        return number.equals(((Const)obj).getNumber());
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    private Number getNumber() {
        return number;
    }
}
