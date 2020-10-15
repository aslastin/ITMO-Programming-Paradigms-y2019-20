package HW.HW13.parserExceptions.ParsingParserExceptions;

import HW.HW13.parserExceptions.ExpressionParserCheckMistake;
import HW.HW13.parserExceptions.ParserException;

public class IllegalLexemParserException extends ParserException {

    public IllegalLexemParserException(String message, ExpressionParserCheckMistake checker) {
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
