package expression.generic.types;


import expression.generic.typeParser.IntegerTypeParser;

public class IntegerType extends AbstractType<Integer> {
    private static IntegerTypeParser parser = new IntegerTypeParser();

    public IntegerType(Integer value) {
        super(value);
    }

    @Override
    public Type<Integer> wrap(Integer arg) {
        return new IntegerType(arg);
    }

    public static Type<Integer> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected Integer applyNegate() {
        return -value;
    }

    @Override
    protected Integer applyMultiply(Integer arg) {
        return value * arg;
    }

    @Override
    protected Integer applyDivide(Integer arg) {
        return value / arg;
    }

    @Override
    protected Integer applySubtract(Integer arg) {
        return value - arg;
    }

    @Override
    protected Integer applyAdd(Integer arg) {
        return value + arg;
    }

    @Override
    protected Integer applyMin(Integer arg) {
        return Math.min(value, arg);
    }

    @Override
    protected Integer applyMax(Integer arg) {
        return Math.max(value, arg);
    }

    @Override
    protected Integer applyCount() {
        return Integer.bitCount(value);
    }
}
