package expression.generic.types;


import expression.generic.typeParser.FloatTypeParser;

public class FloatType extends AbstractType<Float> {
    private static FloatTypeParser parser = new FloatTypeParser();

    public FloatType(Float value) {
        super(value);
    }

    @Override
    public Type<Float> wrap(Float arg) {
        return new FloatType(arg);
    }

    public static Type<Float> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected Float applyNegate() {
        return -value;
    }

    @Override
    protected Float applyMultiply(Float arg) {
        return value * arg;
    }

    @Override
    protected Float applyDivide(Float arg) {
        return value / arg;
    }

    @Override
    protected Float applySubtract(Float arg) {
        return value - arg;
    }

    @Override
    protected Float applyAdd(Float arg) {
        return value + arg;
    }

    @Override
    protected Float applyMin(Float arg) {
        return Math.min(value, arg);
    }

    @Override
    protected Float applyMax(Float arg) {
        return Math.max(value, arg);
    }

    @Override
    protected Float applyCount() {
        return (float)Integer.bitCount(Float.floatToIntBits(value));
    }
}
