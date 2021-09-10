package expression.generic.parser;

import expression.generic.Const;
import expression.generic.Expression;
import expression.generic.Variable;
import expression.generic.binaryOp.*;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalArgParserException;
import expression.generic.parser.token.Token;
import expression.generic.parser.token.TokenType;
import expression.generic.typeParser.TypeParser;
import expression.generic.unaryOp.*;
import expression.generic.parser.exceptions.ParserException;


public class ExpressionParser<T extends Number> implements Parser<T> {
    private Token token;
    private final TypeParser<T> parser;
    private boolean wasRight;

    public ExpressionParser(final TypeParser<T> parser) {
        this.parser = parser;
    }

    @Override
    public Expression<T> parse(final String expression) throws ParserException {
        wasRight = false;
        token = new Token(new StringSource(expression));
        token.next();
        final Expression<T> res = parseExpression(TokenType.TOP.priority());
        if (res == null) {
            token.checkEmptyExpression(null);
        }
        return res;
    }

    private Expression<T> parseExpression(final int priority) throws ParserException {
        Expression<T> expr = null;
        if (priority == 0) {
            if (token.getTokenType() == TokenType.LBRACKET) {
                token.next();
                expr = parseExpression(TokenType.TOP.priority());
                if (token.getTokenType() != TokenType.RBRACKET) {
                    // error
                }
                wasRight = false;
                return expr;
            }
            if (token.getTokenType() == TokenType.CONST || token.getTokenType() == TokenType.VAR) {
                expr = apply();
                token.next();
            }
            return expr;
        }

        while (token.hasNext() && !wasRight && token.getTokenType().priority() <= priority) {
            if (token.getTokenType().priority() == priority) {
                final TokenType currTokenType = token.getTokenType();
                token.next();
                expr = token.isUnary(currTokenType) ? apply(currTokenType, parseExpression(priority)) :
                        apply(currTokenType, expr, parseExpression(priority - 1));
            } else {
                expr = parseExpression(priority - 1);
            }
        }

        return expr;
    }

    private Expression<T> apply(final TokenType tokenType, final Expression<T> arg) throws IllegalArgParserException {
        token.checkEmptyExpression(arg);
        switch (tokenType) {
            case NEGATE: return new Negate<>(arg);
            case COUNT: return new Count<>(arg);
            default: return null;
        }
    }


    private Expression<T> apply() {
        switch (token.getTokenType()) {
            case VAR : {
                return new Variable<>(token.getToken());
            }
            case CONST : {
                return new Const<>(parser.parse(token.getToken()));
            }
        }
        return null;
    }

    private Expression<T> apply(final TokenType tokenType, final Expression<T> firstArg, final Expression<T> secondArg) throws IllegalArgParserException {
        token.checkEmptyExpression(firstArg);
        token.checkEmptyExpression(secondArg);
        switch (tokenType) {
            case ADD : {
                return new Add<>(firstArg, secondArg);
            }
            case SUB : {
                return new Subtract<>(firstArg, secondArg);
            }
            case MUL : {
                return new Multiply<>(firstArg, secondArg);
            }
            case DIV : {
                return new Divide<>(firstArg, secondArg);
            }
            case MAX : {
                return new Max<>(firstArg, secondArg);
            }
            case MIN: {
                return new Min<>(firstArg, secondArg);
            }
        }
        return null;
    }
}

