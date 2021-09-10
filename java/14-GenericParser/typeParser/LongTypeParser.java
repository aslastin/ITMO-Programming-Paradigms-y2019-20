package expression.generic.typeParser;

import expression.generic.types.LongType;
import expression.generic.types.Type;

public class LongTypeParser implements TypeParser<Long>  {
    @Override
    public Type<Long> parse(String number) {
        return new LongType(Long.parseLong(number));
    }

    @Override
    public Type<Long> parse(int value) {
        return new LongType((long)value);
    }
}
