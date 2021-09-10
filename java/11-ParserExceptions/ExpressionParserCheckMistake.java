package HW.HW13.parserExceptions;

import HW.HW11.expression.CommonExpression;
import HW.HW12.parser.ParserSource;
import HW.HW13.parserExceptions.ParsingParserExceptions.IllegalArgParserException;
import HW.HW13.parserExceptions.ParsingParserExceptions.IllegalLexemParserException;
import HW.HW13.parserExceptions.ParsingParserExceptions.IllegalParenthesisParserException;

public class ExpressionParserCheckMistake {
    private int startMistakePos, endMistakePos, bracketBalance;
    private ParserSource parserSource;

    public ExpressionParserCheckMistake(ParserSource parserSource) {
        this.parserSource = parserSource;
    }

    public String getInput() {
        return parserSource.getInput();
    }

    public int getStartMistakePos() {
        return startMistakePos;
    }

    public int getEndMistakePos() {
        return endMistakePos;
    }

    public void upgradeStartMistakePos() {
        startMistakePos = parserSource.getPosition() - 1;
    }

    public void upgradeEndMistakePos() {
        endMistakePos = parserSource.getPosition();
    }

    public void increaseBracketBalance() {
        bracketBalance++;
    }

    public void emptyExpression(Object expression) throws IllegalArgParserException {
        if (expression == null) {
            endMistakePos += endMistakePos == 0 ? 1 : 0;
            throw new IllegalArgParserException("Empty expression. Argument expected.", this);
        }
    }

    public void notBinaryArg(String incorrect, char extra) throws IllegalArgParserException {
        incorrect = incorrect.length() > 0 ? incorrect : "" + extra;
        endMistakePos -= endMistakePos == 0 ? 0 : 1;
        throw new IllegalArgParserException("Expected: Binary Operation\t\tFound: " + incorrect, this);
    }

    public void openBracketBalance() throws IllegalParenthesisParserException {
        if (bracketBalance > 0) {
            endMistakePos -= endMistakePos == 0 ? 0 : 1;
            throw new IllegalParenthesisParserException("Input string contains too many open brackets");
        }
    }

    public void decreaseBracketBalance() throws IllegalParenthesisParserException {
        bracketBalance--;
        if (bracketBalance < 0) {
            endMistakePos -= endMistakePos == 0 ? 0 : 1;
            throw new IllegalParenthesisParserException("Wrong close bracket", this);
        }
    }

    public void notSoloArg(String incorrect, char extra) throws IllegalArgParserException {
        incorrect = incorrect.length() > 0 ? incorrect : "" + extra;
        endMistakePos -= endMistakePos == 0 ? 0 : 1;
        throw new IllegalArgParserException("Expected: Const(Integer)/Variable/Unary Operation\t\tFound: " + incorrect, this);
    }

    public void input(StringBuilder buffer) throws IllegalLexemParserException {
        int MAX_LEXEME_LENGTH = 10;
        if (buffer.length() > MAX_LEXEME_LENGTH) {
            endMistakePos += endMistakePos == 0 ? 1 : 0;
            throw new IllegalLexemParserException("Incorrect lexeme starts with: " + buffer.toString(), this);
        }
    }

}
