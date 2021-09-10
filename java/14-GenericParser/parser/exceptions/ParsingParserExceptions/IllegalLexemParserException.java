package expression.generic.parser.exceptions.ParsingParserExceptions;

import expression.generic.parser.exceptions.ExpressionParserChecker;
import expression.generic.parser.exceptions.ParserException;

public class IllegalLexemParserException extends ParserException {

    public IllegalLexemParserException(String message, ExpressionParserChecker checker) {
        super(message, checker);
    }

    public IllegalLexemParserException(String message) {
        super(message);
    }

    public IllegalLexemParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalLexemParserException(Throwable cause) {
        super(cause);
    }
}
