package HW.HW12.parser;

import HW.HW11.expression.CommonExpression;
import HW.HW11.expression.Const;
import HW.HW11.expression.Variable;
import HW.HW11.expression.binaryOp.*;
import HW.HW11.expression.unaryOp.*;
import HW.HW13.parserExceptions.ParserException;
import HW.HW13.parserExceptions.ParsingParserExceptions.*;
import HW.HW13.parserExceptions.ExpressionParserCheckMistake;

import java.util.*;


public class ExpressionParser implements Parser {
    private ParserSource source;
    private StringBuilder buffer;
    private char accumulator;
    private boolean wasCloseBracket;
    private ExpressionParserCheckMistake checker;
    private final static Map<String, Lexeme> UN_OP = new HashMap<>(Map.of(
            "abs", Lexeme.ABS,  "square", Lexeme.SQUARE, "reverse", Lexeme.REVERSE,
            "digits", Lexeme.DIGITS,  "pow2", Lexeme.POW,  "log2", Lexeme.LOG,  "-", Lexeme.NEGATE
    ));
    private final static Map<String, Lexeme> BIN_OP = new HashMap<>(Map.of(
            "+", Lexeme.ADD, "-", Lexeme.SUB,"*", Lexeme.MUL, "/", Lexeme.DIV,
            ">>", Lexeme.RIGHT_SHIFT, "<<", Lexeme.LEFT_SHIFT,  "**", Lexeme.POW,  "//", Lexeme.LOG
    ));

    @Override
    public CommonExpression parse(String expression) throws ParserException {
        source = new StringSource(expression);
        buffer = new StringBuilder();
        checker = new ExpressionParserCheckMistake(source);
        wasCloseBracket = false;
        readNext();
        CommonExpression result;
        result = parseSoloExpression(Lexeme.BASE.priority());
        checker.openBracketBalance();
        return result;
    }

  private CommonExpression parseSoloExpression(int priority) throws ParserException {
      CommonExpression expression = null;
      if (isLeftBracket()) {
          readNext();
          checker.increaseBracketBalance();
          expression = parseSoloExpression(Lexeme.BASE.priority());
          wasCloseBracket = false;
          return parseExpression(expression, priority);
      }
      if (!isEnd()) {
          updateUnaryArg();
          String token = getStringLexeme();
          if (UN_OP.containsKey(token)) {
              Lexeme operation = UN_OP.get(token);
              clear();
              expression = apply(operation, parseSoloExpression(operation.priority()));
              if (wasCloseBracket) {
                  return expression;
              }
          } else {
              expression = getVarConst(token);
          }
      }
      checker.emptyExpression(expression);
      return parseExpression(expression, priority);
  }

    private CommonExpression parseExpression(CommonExpression expression, int priority) throws ParserException {
        if (!isEnd()) {
            if (isRightBracket()) {
                readNext();
                checker.decreaseBracketBalance();
                wasCloseBracket = true;
                return expression;
            }
            updateBinaryArg();
            Lexeme operation = getBinaryOperation();
            if (operation.priority() <= priority) {
                return expression;
            }
            while (!isEnd() && priority < (operation = getBinaryOperation()).priority()) {
                clear();
                expression = apply(operation, expression, parseSoloExpression(operation.priority()));
                if (wasCloseBracket) {
                    return expression;
                }
            }
        }
        checker.emptyExpression(expression);
        return expression;
    }

    private CommonExpression apply(Lexeme operation, CommonExpression expression) {
        switch (operation){
            case NEGATE: {
                return new CheckedNegate(expression);
            }
            case POW : {
                return new Pow(expression);
            }
            case LOG : {
                return new Log(expression);
            }
            case ABS : {
                return new Abs(expression);
            }
            case SQUARE : {
                return new Square(expression);
            }
            case REVERSE : {
                return new Reverse(expression);
            }
            case DIGITS : {
                return new Digits(expression);
            }
        }
        return null;
    }

    private CommonExpression apply(Lexeme operation, CommonExpression right, CommonExpression left) {
        switch (operation) {
            case ADD : {
                return new CheckedAdd(right, left);
            }
            case SUB : {
                return new CheckedSubtract(right, left);
            }
            case MUL: {
                return new CheckedMultiply(right, left);
            }
            case DIV : {
                return new CheckedDivide(right, left);
            }
            case LEFT_SHIFT: {
                return new LeftShift(right, left);
            }
            case RIGHT_SHIFT: {
                return new RightShift(right, left);
            }
            case POW : {
                return new Pow(right, left);
            }
            case LOG : {
                return new Log(left, right);
            }
        }
        return null;
    }

    private boolean isVar(String value) {
        return value.equals("x") || value.equals("y") || value.equals("z");
    }

    private CommonExpression getVarConst(String token) throws IllegalArgParserException {
        if (isVar(token)) {
            clear();
            return new Variable(token);
        }
        try {
            int a = Integer.parseInt(token);
            clear();
            return new Const(a);
        } catch(NumberFormatException e) {
            checker.notSoloArg(token, accumulator);
        }
        return null;
    }

    private void appendBuff() throws IllegalLexemParserException {
        checker.input(buffer);
        buffer.append(accumulator);
    }

    private void updateUnaryArg() throws IllegalLexemParserException{
        skipWhiteSpaces();
        checker.upgradeStartMistakePos();
        if (accumulator == '-') {
            appendBuff();
            accumulator = source.next();
            skipWhiteSpaces();
            while (Character.isDigit(accumulator)) {
                appendBuff();
                accumulator = source.next();
            }
        } else {
            while (Character.isDigit(accumulator) || Character.isLetter(accumulator)) {
                appendBuff();
                accumulator = source.next();
            }
        }
        checker.upgradeEndMistakePos();
        skipWhiteSpaces();
    }

    private void updateBinaryArg() throws IllegalLexemParserException {
        skipWhiteSpaces();
        checker.upgradeStartMistakePos();
        while (!(Character.isDigit(accumulator) || Character.isLetter(accumulator) ||
                accumulator == ' ' || accumulator == '\t' || accumulator == '(' || accumulator == ')' || isEnd())
                && !(buffer.length() > 0 && accumulator == '-')) {
            appendBuff();
            accumulator = source.next();
        }
        checker.upgradeEndMistakePos();
        skipWhiteSpaces();
    }

    private void skipWhiteSpaces() {
        while (accumulator == ' ' || accumulator == '\t') {
            accumulator = source.next();
        }
    }

    private boolean isEnd() {
        return accumulator == '\0' && source.getPosition() > source.getInputLength() && buffer.length() == 0;
    }

    private void readNext() {
        accumulator = source.next();
        skipWhiteSpaces();
    }

    private boolean isLeftBracket() {
        skipWhiteSpaces();
        return accumulator == '(' && buffer.length() == 0;
    }

    private boolean isRightBracket() {
        skipWhiteSpaces();
        return accumulator == ')' && buffer.length() == 0;
    }

    private String getStringLexeme() {
        return buffer.toString();
    }

    private void clear() {
        buffer.delete(0, buffer.length());
    }

    private Lexeme getBinaryOperation() throws IllegalArgParserException {
        String token = buffer.toString();
        if (BIN_OP.containsKey(token)) {
            return BIN_OP.get(token);
        }
        checker.notBinaryArg(token, accumulator);
        return null;
    }
}

