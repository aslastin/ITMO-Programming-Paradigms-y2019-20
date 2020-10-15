package expression.generic.parser.exceptions.ArithmeticParserExceptions;

import expression.generic.parser.exceptions.ArithmeticExpressionException;

public class DBZParserException extends ArithmeticExpressionException {

    public DBZParserException(String problem, Throwable cause) {
        super("division by zero", cause, problem);
    }

    public DBZParserException(String problem) {
        super("division by zero", problem);
    }

    public DBZParserException(Throwable cause) {
        super(cause);
    }
}
