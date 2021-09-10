package HW.HW13.parserExceptions.ParsingParserExceptions;

import HW.HW13.parserExceptions.ExpressionParserCheckMistake;
import HW.HW13.parserExceptions.ParserException;

public class IllegalArgParserException extends ParserException {

    public IllegalArgParserException(String message, ExpressionParserCheckMistake checker) {
        super(message, checker);
    }

    public IllegalArgParserException(String message) {
        super(message);
    }

    public IllegalArgParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgParserException(Throwable cause) {
        super(cause);
    }
}
