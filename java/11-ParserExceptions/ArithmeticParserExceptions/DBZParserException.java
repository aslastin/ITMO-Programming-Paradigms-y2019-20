package HW.HW13.parserExceptions.ArithmeticParserExceptions;

import HW.HW13.parserExceptions.ArithmeticExpressionException;
import HW.HW13.parserExceptions.ParserException;

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
