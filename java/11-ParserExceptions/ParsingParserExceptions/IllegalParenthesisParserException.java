package HW.HW13.parserExceptions.ParsingParserExceptions;

import HW.HW13.parserExceptions.ExpressionParserCheckMistake;
import HW.HW13.parserExceptions.ParserException;

public class IllegalParenthesisParserException extends ParserException {
    public IllegalParenthesisParserException(String message, ExpressionParserCheckMistake checker) {
        super(message, checker);
    }

    public IllegalParenthesisParserException(String message) {
        super(message);
    }

    public IllegalParenthesisParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParenthesisParserException(Throwable cause) {
        super(cause);
    }
}
