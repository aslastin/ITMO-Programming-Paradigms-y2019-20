package expression.generic.typeParser;

import expression.generic.types.FloatType;
import expression.generic.types.Type;

public class FloatTypeParser implements TypeParser<Float> {
    @Override
    public Type<Float> parse(String number) {
        return new FloatType(Float.parseFloat(number));
    }

    @Override
    public Type<Float> parse(int value) {
        return new FloatType((float)value);
    }
}
