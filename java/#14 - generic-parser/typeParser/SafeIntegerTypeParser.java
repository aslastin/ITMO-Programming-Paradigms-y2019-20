package expression.generic.typeParser;

import expression.generic.types.IntegerType;
import expression.generic.types.SafeIntegerType;
import expression.generic.types.Type;

public class SafeIntegerTypeParser implements TypeParser<Integer> {
    public Type<Integer> parse(String value) {
        return new SafeIntegerType(Integer.parseInt(value));
    }

    @Override
    public Type<Integer> parse(int value) {
        return new SafeIntegerType(value);
    }
}
