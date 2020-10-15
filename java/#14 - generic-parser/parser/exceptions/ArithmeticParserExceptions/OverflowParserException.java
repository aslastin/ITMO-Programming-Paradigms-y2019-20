package expression.generic.parser.exceptions.ArithmeticParserExceptions;


import expression.generic.parser.exceptions.ArithmeticExpressionException;

public class OverflowParserException extends ArithmeticExpressionException {

    public OverflowParserException(String problem, Throwable cause) {
        super("overflow", cause, problem);
    }

    public OverflowParserException(String problem) {
        super("overflow", problem);
    }

    public OverflowParserException(Throwable cause) {
        super(cause);
    }
}
