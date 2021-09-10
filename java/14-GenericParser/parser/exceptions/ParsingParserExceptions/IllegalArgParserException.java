package expression.generic.parser.exceptions.ParsingParserExceptions;

import expression.generic.parser.exceptions.ExpressionParserChecker;
import expression.generic.parser.exceptions.ParserException;

public class IllegalArgParserException extends ParserException {

    public IllegalArgParserException(String message, ExpressionParserChecker checker) {
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
