package expression.generic.typeParser;

import expression.generic.types.DoubleType;
import expression.generic.types.Type;

public class DoubleTypeParser implements TypeParser<Double> {
    @Override
    public Type<Double> parse(String number) {
        return new DoubleType(Double.parseDouble(number));
    }

    @Override
    public Type<Double> parse(int value) {
        return new DoubleType((double)value);
    }
}
