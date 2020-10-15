package expression.generic.types;


import expression.generic.typeParser.ShortTypeParser;

public class ShortType extends AbstractType<Short> {
    private static ShortTypeParser parser = new ShortTypeParser();

    public ShortType(Short value) {
        super(value);
    }

    @Override
    public Type<Short> wrap(Short arg) {
        return new ShortType(arg);
    }

    public static Type<Short> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected Short applyNegate() {
        return (short)(-value);
    }

    @Override
    protected Short applyMultiply(Short arg) {
        return (short)(value * arg);
    }

    @Override
    protected Short applyDivide(Short arg) {
        return (short)(value / arg);
    }

    @Override
    protected Short applySubtract(Short arg) {
        return (short)(value - arg);
    }

    @Override
    protected Short applyAdd(Short arg) {
        return (short)(value + arg);
    }

    @Override
    protected Short applyMin(Short arg) {
        return (short)(Math.min(value, arg));
    }

    @Override
    protected Short applyMax(Short arg) {
        return (short)(Math.max(value, arg));
    }

    @Override
    protected Short applyCount() {
        if (value < 0) {
            return (short)(Short.SIZE - Integer.bitCount(~value));
        }
        return (short)Integer.bitCount(value);
    }
}
