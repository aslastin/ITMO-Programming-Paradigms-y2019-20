package HW.HW11.expression;


import HW.HW12.parser.Lexeme;

public class Variable implements CommonExpression {
    private String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public Lexeme getLexeme() {
        return Lexeme.VAR;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (variable.equals("x")) {
            return x;
        }
        if (variable.equals("y")) {
            return y;
        }
        if (variable.equals("z")) {
            return z;
        }
        throw new IllegalStateException("Incorrect input");
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
