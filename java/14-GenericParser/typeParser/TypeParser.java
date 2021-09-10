package expression.generic.typeParser;

import expression.generic.types.Type;

public interface TypeParser<T extends Number> {
    Type<T> parse(String number);
    Type<T> parse(int value);
}
