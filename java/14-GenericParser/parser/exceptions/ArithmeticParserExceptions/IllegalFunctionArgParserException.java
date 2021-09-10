package expression.generic.parser.exceptions.ArithmeticParserExceptions;

import expression.generic.parser.exceptions.ArithmeticExpressionException;

public class IllegalFunctionArgParserException extends ArithmeticExpressionException {

    public IllegalFunctionArgParserException(String message, String problem, Throwable cause) {
        super(message, cause, problem);
    }

    public IllegalFunctionArgParserException(String message, String problem) {
        super(message, problem);
    }

    public IllegalFunctionArgParserException(String message) {
        super(message);
    }

    public IllegalFunctionArgParserException(Throwable cause) {
        super(cause);
    }
}
