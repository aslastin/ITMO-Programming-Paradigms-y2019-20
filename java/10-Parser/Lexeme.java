package HW.HW12.parser;


public enum Lexeme {
    MIN(5), MAX(5),
    LEFT_SHIFT(10), RIGHT_SHIFT(10),
    SUB(15), ADD(15),
    MUL(20), DIV(20),
    NEGATE(25), ABS(25), SQUARE(25), REVERSE(25), DIGITS(25), LOG(25), POW(25),
    COUNT(25),
    CONST(0), VAR(0), BASE(0);

    private int priority;

    Lexeme(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }
}
