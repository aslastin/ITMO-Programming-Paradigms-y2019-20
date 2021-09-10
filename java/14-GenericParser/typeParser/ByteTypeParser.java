package expression.generic.typeParser;

import expression.generic.types.ByteType;
import expression.generic.types.Type;

public class ByteTypeParser implements TypeParser<Byte> {
    @Override
    public Type<Byte> parse(String number) {
        return new ByteType(Byte.parseByte(number));
    }

    @Override
    public Type<Byte> parse(int value) {
        return new ByteType((byte)value);
    }
}
