package HW.HW11.expression;

import HW.HW12.parser.Lexeme;

public interface CommonExpression extends TripleExpression, Expression, DoubleExpression {
    Lexeme getLexeme();
}
