package expression.generic.parser.exceptions.ArithmeticParserExceptions;

import expression.generic.parser.exceptions.ArithmeticExpressionException;

public class UnderflowParserException extends ArithmeticExpressionException {
    public UnderflowParserException(String problem, Throwable cause) {
        super("underflow", cause, problem);
    }

    public UnderflowParserException(String problem) {
        super("underflow", problem);
    }

    public UnderflowParserException(Throwable cause) {
        super(cause);
    }
}
