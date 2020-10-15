package expression.generic.typeParser;

import expression.generic.types.IntegerType;
import expression.generic.types.Type;

public class IntegerTypeParser implements TypeParser<Integer> {
    @Override
    public Type<Integer> parse(String number) {
        return new IntegerType(Integer.parseInt(number));
    }

    @Override
    public Type<Integer> parse(int value) {
        return new IntegerType(value);
    }
}
