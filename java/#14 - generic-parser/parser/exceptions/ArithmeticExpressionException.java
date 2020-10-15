package expression.generic.parser.exceptions;

public class ArithmeticExpressionException extends Exception {
    String problem;

    public ArithmeticExpressionException(String message, String problem) {
        super(message);
        this.problem = problem;
    }

    public ArithmeticExpressionException(String message, Throwable cause, String problem) {
        super(message, cause);
        this.problem = problem;
    }

    public ArithmeticExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArithmeticExpressionException(String message) {
        super(message);
    }

    public ArithmeticExpressionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\t\tError: " + problem;
    }

}