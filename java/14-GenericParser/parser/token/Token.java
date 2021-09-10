package expression.generic.parser.token;

import expression.generic.Expression;
import expression.generic.parser.ParserSource;
import expression.generic.parser.exceptions.ExpressionParserChecker;
import expression.generic.parser.exceptions.ParserException;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalArgParserException;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalLexemParserException;
import expression.generic.parser.exceptions.ParsingParserExceptions.IllegalParenthesisParserException;
import expression.generic.types.IntegerType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Token extends AbstractProtectedToken {
    private final static Map<String, TokenType> UN_OP = new HashMap<>(Map.of(
            "-", TokenType.NEGATE, "count", TokenType.COUNT
    ));
    private final static Map<String, TokenType> BIN_OP = new HashMap<>(Map.of(
            "+", TokenType.ADD, "-", TokenType.SUB,"*", TokenType.MUL, "/", TokenType.DIV,
            "min", TokenType.MIN, "max", TokenType.MAX
    ));

    public Token(ParserSource input, Predicate<Character> skip, Predicate<Character> isUnary, Predicate<Character> isBinary) {
        super(input, skip, isUnary, isBinary);
    }

    public Token(ParserSource input) {
        super(input, ch -> ch == ' ' || ch == '\t', ch -> Character.isDigit(ch) || Character.isLetter(ch),
                 new Predicate<>() {
                    private char prev = '\0';
                    private int count = 0;
                    @Override
                    public boolean test(Character character) {
                        boolean res = !(character == ' ' || character == '\t' || character == '(' || character == ')' || skipPrevOp()
                                || count > 0 && character == '-');
                        if (res) {
                            count++;
                            prev = character;
                        } else {
                            count = 0;
                            prev = '\0';
                        }
                        return res;
                    }

                    private boolean skipPrevOp() {
                        return prev == '-' || prev == '*' || prev == '/' || prev == '+';
                    }
                }
        );
    }

    @Override
    protected TokenType getBinaryToken(String token) {
        return BIN_OP.getOrDefault(token, null);
    }

    @Override
    protected TokenType getUnaryToken(String token) {
        return UN_OP.getOrDefault(token, null);
    }

    @Override
    public boolean isUnary(String token) {
        return UN_OP.containsKey(token);
    }

    @Override
    public boolean isBinary(TokenType tokenType) {
        return BIN_OP.containsValue(tokenType);
    }

    @Override
    public boolean isBinary(String token) {
        return BIN_OP.containsKey(token);
    }

    @Override
    protected boolean isConst(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public boolean isUnary(TokenType tokenType) {
        return UN_OP.containsValue(tokenType);
    }

    protected boolean isVar(String value) {
        return value.equals("x") || value.equals("y") || value.equals("z");
    }

}
