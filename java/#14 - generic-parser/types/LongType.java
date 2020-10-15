package expression.generic.types;


import expression.generic.typeParser.LongTypeParser;

public class LongType extends AbstractType<Long> {
    private static LongTypeParser parser = new LongTypeParser();

    public LongType(Long value) {
        super(value);
    }

    @Override
    public Type<Long> wrap(Long arg) {
        return new LongType(arg);
    }

    public static Type<Long> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected Long applyNegate() {
        return -value;
    }

    @Override
    protected Long applyMultiply(Long arg) {
        return value * arg;
    }

    @Override
    protected Long applyDivide(Long arg) {
        return value / arg;
    }

    @Override
    protected Long applySubtract(Long arg) {
        return value - arg;
    }

    @Override
    protected Long applyAdd(Long arg) {
        return value + arg;
    }

    @Override
    protected Long applyMin(Long arg) {
        return Math.min(value, arg);
    }

    @Override
    protected Long applyMax(Long arg) {
        return Math.max(value, arg);
    }

    @Override
    protected Long applyCount() {
        return (long)Long.bitCount(value);
    }
}
