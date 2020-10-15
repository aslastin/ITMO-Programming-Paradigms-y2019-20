package expression.generic.types;

import expression.generic.parser.exceptions.ArithmeticParserExceptions.DBZParserException;
import expression.generic.parser.exceptions.ArithmeticParserExceptions.OverflowParserException;
import expression.generic.parser.exceptions.ArithmeticParserExceptions.UnderflowParserException;
import expression.generic.typeParser.SafeIntegerTypeParser;

public class SafeIntegerType extends AbstractType<Integer> {
    public static SafeIntegerTypeParser parser = new SafeIntegerTypeParser();

    public SafeIntegerType(final Integer value) {
        super(value);
    }

    @Override
    public Type<Integer> wrap(final Integer arg) {
        return new SafeIntegerType(arg);
    }

    public static Type<Integer> parse(final String value) {
        return parser.parse(value);
    }

    @Override
    protected Integer applyNegate() throws OverflowParserException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowParserException("- " + value);
        }
        return -value;
    }

    @Override
    protected Integer applyMultiply(final Integer arg) throws OverflowParserException, UnderflowParserException {
        if (value == 0 || arg == 0) {
            return 0;
        }
        if (value > 0) {
            if (arg > 0) {
                if (value > Integer.MAX_VALUE / arg) {
                    throw new OverflowParserException(value + " * " + arg);
                }
            } else {
                if (arg < Integer.MIN_VALUE / value) {
                    throw new UnderflowParserException(value + " * " + arg);
                }
            }
        } else {
            if (arg > 0) {
                if (value < Integer.MIN_VALUE / arg) {
                    throw new UnderflowParserException(value + " * " + arg);
                }
            } else {
                if (value < Integer.MAX_VALUE / arg) {
                    throw new OverflowParserException(value + " * " + arg);
                }
            }
        }
        return value * arg;
    }

    @Override
    protected Integer applyDivide(final Integer arg) throws OverflowParserException, DBZParserException {
        if (arg == 0) {
            throw new DBZParserException(value + " / " + arg);
        }
        if (value == Integer.MIN_VALUE && arg == -1) {
            throw new OverflowParserException(value + " / " + arg);
        }
        return value / arg;
    }

    @Override
    protected Integer applySubtract(final Integer arg) throws OverflowParserException, UnderflowParserException {
        if (value == 0 && arg == Integer.MIN_VALUE) {
            throw new OverflowParserException(value + " - " + arg);
        }
        if (value > 0 && arg < 0 && value > Integer.MAX_VALUE + arg) {
            throw new OverflowParserException(value + " - " + arg);
        }
        if (value < 0 && arg > 0 && value < Integer.MIN_VALUE + arg) {
            throw new UnderflowParserException(value + " - " + arg);
        }
        return value - arg;
    }

    @Override
    protected Integer applyAdd(final Integer arg) throws OverflowParserException, UnderflowParserException {
        if (value == 0 || arg  == 0) {
            return value + arg;
        }
        if (value > 0 && arg  > 0 && value > Integer.MAX_VALUE - arg) {
            throw new OverflowParserException(value + " + " + arg);
        }
        if (value < 0 && arg  < 0 && value < Integer.MIN_VALUE - arg) {
            throw new UnderflowParserException(value + " + " + arg);
        }
        return value + arg;
    }

    @Override
    protected Integer applyMin(final Integer arg) {
        return Math.min(value, arg);
    }

    @Override
    protected Integer applyMax(final Integer arg) {
        return Math.max(value, arg);
    }

    @Override
    protected Integer applyCount() {
        return Integer.bitCount(value);
    }
}
