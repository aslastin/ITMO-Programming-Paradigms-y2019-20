package HW.HW13.parserExceptions.ArithmeticParserExceptions;

import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ParserException;

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
