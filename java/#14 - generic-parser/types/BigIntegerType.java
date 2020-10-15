package expression.generic.types;


import expression.generic.typeParser.BigIntegerTypeParser;

import java.math.BigInteger;

public class BigIntegerType extends AbstractType<BigInteger> {
    private static BigIntegerTypeParser parser = new BigIntegerTypeParser();

    public BigIntegerType(BigInteger value) {
        super(value);
    }

    @Override
    public Type<BigInteger> wrap(BigInteger arg) {
        return new BigIntegerType(arg);
    }

    public static Type<BigInteger> parse(String value) {
        return parser.parse(value);
    }

    @Override
    protected BigInteger applyNegate() {
        return value.negate();
    }

    @Override
    protected BigInteger applyMultiply(BigInteger arg) {
        return value.multiply(arg);
    }

    @Override
    protected BigInteger applyDivide(BigInteger arg) {
        return value.divide(arg);
    }

    @Override
    protected BigInteger applySubtract(BigInteger arg) {
        return value.subtract(arg);
    }

    @Override
    protected BigInteger applyAdd(BigInteger arg) {
        return value.add(arg);
    }

    @Override
    protected BigInteger applyMin(BigInteger arg) {
        return value.min(arg);
    }

    @Override
    protected BigInteger applyMax(BigInteger arg) {
        return value.max(arg);
    }

    @Override
    protected BigInteger applyCount() {
        return BigInteger.valueOf(value.bitCount());
    }
}
