package expression.generic.parser.token;

public enum TokenType {
    TOP(4),
    MIN(4), MAX(4),
    SUB(3), ADD(3),
    MUL(2), DIV(2),
    NEGATE(1), COUNT(1),
    CONST(0), VAR(0), END(0), LBRACKET(0), RBRACKET(0),;

    private int priority;

    TokenType(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }
}
