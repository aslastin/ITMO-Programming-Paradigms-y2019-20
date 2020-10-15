package expression.generic.typeParser;

import expression.generic.types.ShortType;
import expression.generic.types.Type;

public class ShortTypeParser implements TypeParser<Short> {
    @Override
    public Type<Short> parse(String number) {
        return new ShortType(Short.parseShort(number));
    }

    @Override
    public Type<Short> parse(int value) {
        return new ShortType((short)value);
    }
}
