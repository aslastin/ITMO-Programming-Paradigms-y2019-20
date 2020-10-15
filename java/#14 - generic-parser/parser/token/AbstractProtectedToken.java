package expression.generic.parser.token;

import expression.generic.parser.ParserSource;
import expression.generic.parser.exceptions.ExpressionParserChecker;
import expression.generic.parser.exceptions.ParserException;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalArgParserException;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalLexemParserException;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalParenthesisParserException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class AbstractProtectedToken {
    private ParserSource input;
    private TokenType tokenType;
    private StringBuilder buffer;
    private char accumulator;
    private boolean isSingle = true;
    private ExpressionParserChecker checker;
    private Predicate<Character> skip, isBinary, isUnary;


    public AbstractProtectedToken(ParserSource input, Predicate<Character> skip,
                                  Predicate<Character> isUnary, Predicate<Character> isBinary) {
        this.input = input;
        this.skip = skip;
        this.isUnary = isUnary;
        this.isBinary = isBinary;
        buffer = new StringBuilder();
        accumulator = this.input.next();
        checker = new ExpressionParserChecker(this.input);
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return buffer.toString();
    }

    public void next() throws ParserException {
        if (isEnd()) return;
        buffer.delete(0, buffer.length());
        if (isSingle) {
            updateUnaryArg();
        } else {
            updateBinaryArg();
        }
    }

    public boolean hasNext() throws IllegalParenthesisParserException {
        if (isEnd()) {
            checker.openBracketBalance();
        }
        return !isEnd();
    }

    public void checkEmptyExpression(Object expression) throws IllegalArgParserException {
        checker.emptyExpression(expression);
    }

    private void appendBuff() throws IllegalLexemParserException {
        checker.input(buffer);
        buffer.append(accumulator);
    }

    private void updateUnaryArg() throws IllegalLexemParserException, IllegalArgParserException, IllegalParenthesisParserException {
        skip();
        checker.upgradeStartMistakePos();
        if (accumulator == '-') {
            appendBuff();
            accumulator = input.next();
            skip();
            while (!isEnd() && Character.isDigit(accumulator)) {
                appendBuff();
                accumulator = input.next();
            }
        } else {
            while (!isEnd() && isUnary.test(accumulator)) {
                appendBuff();
                accumulator = input.next();
            }
        }
        checker.upgradeEndMistakePos();
        updateTokenType();
    }

    private void updateBinaryArg() throws ParserException {
        skip();
        checker.upgradeStartMistakePos();
        while (!isEnd() && isBinary.test(accumulator)) {
            appendBuff();
            accumulator = input.next();
        }
        checker.upgradeEndMistakePos();
        updateTokenType();
    }

    private void updateTokenType() throws IllegalArgParserException, IllegalParenthesisParserException {
        if (isEnd()) {
            tokenType = TokenType.END;
            return;
        }
        if (buffer.length() == 0 && (accumulator == '(' || accumulator == ')')) {
            if (accumulator == '(') {
                tokenType = TokenType.LBRACKET;
                checker.increaseBracketBalance();
            } else {
                tokenType = TokenType.RBRACKET;
                checker.decreaseBracketBalance();
            }
            accumulator = input.next();
            return;
        }
        String string = buffer.toString();
        if (isSingle) {
            if (isUnary(string)) {
                tokenType = getUnaryToken(string);
            } else {
                if (isVar(string)) {
                    tokenType = TokenType.VAR;
                } else {
                    if (isConst(string)) {
                        tokenType = TokenType.CONST;
                    } else {
                        checker.notSoloArg(string, accumulator);
                    }
                }
                isSingle = false;
            }
        } else {
            if (isBinary(string)) {
                tokenType = getBinaryToken(string);
                isSingle = true;
            } else {
                checker.notBinaryArg(string, accumulator);
            }
        }
    }

    protected abstract TokenType getBinaryToken(String token);

    protected abstract TokenType getUnaryToken(String token);

    public abstract boolean isUnary(TokenType tokenType);

    public abstract boolean isUnary(String token);

    public abstract boolean isBinary(TokenType tokenType);

    public abstract boolean isBinary(String token);

    protected abstract boolean isVar(String value);

    protected abstract boolean isConst(String value);

    private void skip() {
        while (!isEnd() && skip.test(accumulator)) {
            accumulator = input.next();
        }
    }

    private boolean isEnd() {
        return accumulator == '\0' && input.getPosition() == input.getInput().length() && buffer.length() == 0;
    }
}
