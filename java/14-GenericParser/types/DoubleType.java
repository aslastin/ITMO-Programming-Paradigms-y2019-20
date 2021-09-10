package expression.generic.types;

import expression.generic.typeParser.DoubleTypeParser;

public  class DoubleType extends AbstractType<Double> {
    private static DoubleTypeParser parser = new DoubleTypeParser();

    public DoubleType(Double value) {
        super(value);
    }

    @Override
    public Type<Double> wrap(Double arg) {
        return new DoubleType(arg);
    }

    public static Type<Double> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected Double applyNegate() {
        return -value;
    }

    @Override
    protected Double applyMultiply(Double arg) {
        return value * arg;
    }

    @Override
    protected Double applyDivide(Double arg) {
        return value / arg;
    }

    @Override
    protected Double applySubtract(Double arg) {
        return value - arg;
    }

    @Override
    protected Double applyAdd(Double arg) {
        return value + arg;
    }

    @Override
    protected Double applyMin(Double arg) {
        return Math.min(value, arg);
    }

    @Override
    protected Double applyMax(Double arg) {
        return Math.max(value, arg);
    }

    @Override
    protected Double applyCount() {
        return (double) Long.bitCount(Double.doubleToLongBits(value));
    }
}
