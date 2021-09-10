package HW.HW13.parserExceptions.ArithmeticParserExceptions;

import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ParserException;

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
