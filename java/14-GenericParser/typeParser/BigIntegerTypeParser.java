package expression.generic.typeParser;

import expression.generic.types.BigIntegerType;
import expression.generic.types.Type;

import java.math.BigInteger;

public class BigIntegerTypeParser implements TypeParser<BigInteger> {
    @Override
    public Type<BigInteger> parse(final String number) {
        return new BigIntegerType(new BigInteger(number));
    }

    @Override
    public Type<BigInteger> parse(final int value) {
        return new BigIntegerType(new BigInteger(String.valueOf(value)));
    }
}
