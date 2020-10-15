package expression.generic.types;


import expression.generic.typeParser.ByteTypeParser;

public class ByteType extends AbstractType<Byte> {
    private static ByteTypeParser parser = new ByteTypeParser();

    public ByteType(Byte value) {
        super(value);
    }

    @Override
    public Type<Byte> wrap(Byte arg) {
        return new ByteType(arg);
    }

    public static Type<Byte> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected Byte applyNegate() {
        return (byte)(-value);
    }

    @Override
    protected Byte applyMultiply(Byte arg) {
        return (byte)(value * arg);
    }

    @Override
    protected Byte applyDivide(Byte arg) {
        return (byte)(value / arg);
    }

    @Override
    protected Byte applySubtract(Byte arg) {
        return (byte)(value - arg);
    }

    @Override
    protected Byte applyAdd(Byte arg) {
        return (byte)(value + arg);
    }

    @Override
    protected Byte applyMin(Byte arg) {
        return (byte)Math.min(value, arg);
    }

    @Override
    protected Byte applyMax(Byte arg) {
        return (byte)Math.max(value, arg);
    }

    @Override
    protected Byte applyCount() {
        if (value < 0) {
            return (byte)(Byte.SIZE - Integer.bitCount(~value));
        }
        return (byte)Integer.bitCount(value);
    }
}
