package expression.generic.parser.exceptions;


public class ParserException extends Exception {
    private String invalidPos = null, input = null;
    private StringBuilder locality = null;


    public ParserException(String message, int invalidPos) {
        super(message);
        this.invalidPos = "\t\tInvalid position: " + invalidPos + '\t';
    }

    public ParserException(String message, ExpressionParserChecker checker) {
        this(message, checker.getEndMistakePos());
        input = checker.getInput();
        try {
            locality = new StringBuilder("\tError's locality:\t");
            int shift = 30;
            if (checker.getStartMistakePos() - shift > 0) {
                locality.append("...").append(checker.getInput(), checker.getStartMistakePos() - shift, checker.getStartMistakePos());
            } else {
                locality.append(checker.getInput(), 0, checker.getStartMistakePos());
            }
            locality.append(" <EXPECTED> ");
            if (checker.getEndMistakePos() + shift >= checker.getInput().length()) {
                if (checker.getEndMistakePos()  != checker.getInput().length() - 1) {
                    locality.append(checker.getInput(), checker.getEndMistakePos() , checker.getInput().length());
                }
            } else {
                locality.append(checker.getInput(), checker.getEndMistakePos() , checker.getEndMistakePos()  + shift).append("...");
            }
        } catch (IndexOutOfBoundsException e) {
            locality = new StringBuilder("\t\tError input:\t" + checker.getInput());
        }
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder();
            message.append(super.getMessage());
            if (invalidPos != null) {
                message.append(invalidPos);
            }
            if (locality != null) {
                message.append(locality);
            }
        return message.toString();
    }

    public String getParsedString() {
        return input;
    }
}
