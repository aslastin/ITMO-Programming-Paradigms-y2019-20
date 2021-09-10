package expression.generic.parser.exceptions.ParsingParserExceptions;

import expression.generic.parser.exceptions.ExpressionParserChecker;
import expression.generic.parser.exceptions.ParserException;

public class IllegalParenthesisParserException extends ParserException {
    public IllegalParenthesisParserException(String message, ExpressionParserChecker checker) {
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
