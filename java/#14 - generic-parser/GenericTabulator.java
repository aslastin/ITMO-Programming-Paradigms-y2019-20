package expression.generic;


import expression.generic.parser.exceptions.ArithmeticExpressionException;
import expression.generic.parser.exceptions.ParserException;
import expression.generic.parser.ExpressionParser;
import expression.generic.typeParser.*;
import expression.generic.types.*;
import java.util.HashMap;
import java.util.Map;


public class GenericTabulator implements Tabulator {

    private static final Map<String, TypeParser<? extends Number>> MODE_PARSER = new HashMap<>(Map.of(
            "i", new SafeIntegerTypeParser(), "d", new DoubleTypeParser(),
            "bi", new BigIntegerTypeParser(), "s", new ShortTypeParser(),
            "l", new LongTypeParser(), "u", new IntegerTypeParser(),
            "f", new FloatTypeParser(), "b", new ByteTypeParser()
    ));

    @Override
    public Object[][][] tabulate(final String mode, final String expression, final int x1, final int x2, final int y1, final int y2, final int z1, final int z2) throws ParserException {
        final TypeParser<? extends Number> parser = MODE_PARSER.getOrDefault(mode, null);
        if (parser == null) {
            throw new IllegalStateException("Incorrect mode");
        }
        return buildTable(expression, parser, x1, x2, y1, y2, z1, z2);
    }

    private <T extends Number> Object[][][] buildTable(final String string, final TypeParser<T> parser, final int x1, final int x2, final int y1, final int y2, final int z1, final int z2) throws ParserException {
        final Expression<T> expression = new ExpressionParser<>(parser).parse(string);
        final Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    try {
                        final Type<T> res = expression.evaluate(parser.parse(x1 + i), parser.parse(y1 + j), parser.parse(z1 + k));
                        table[i][j][k] = res.value();
                    } catch (final ArithmeticExpressionException | ArithmeticException ignored) {}
                }
            }
        }
        return table;
    }

}
