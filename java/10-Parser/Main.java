package HW.HW12.parser;

import HW.HW11.expression.CommonExpression;
import HW.HW13.parserExceptions.ParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, ParserException {
        List<String> listOfMistakes = new ArrayList<>(List.of(
                "-(-(-		-5 + 16   *x) + 1 * z) -(((-11)))".repeat(2) + "-(-(-		-5  16   *x*y) +  1 * z) -(((-11)))",
                "-(-(-		-5 + 16   *x*y) + 1 * z) -(((-11)))".repeat(2) + "-(-(-		-5 + 16   *x*y)  1 * z) -(((-11)))",
                "(".repeat(500) + "x + y  (-10*-z)" + ")".repeat(500),
                "x*y+(z-1   )10+".repeat(3) + "x",
                "x + y +  \0",
                "\0\0\0",
                "x**y",
                "x*y+(z-1   )/10",
                "x--y--z",
                "1024 >> 5 + 3",
                "((2+2))-0/((--2)*555)",
                "-(-(-\t\t-5 + 16   *x*y) + 1 * z) -(((-11)))"
        ));
        ExpressionParser parser = new ExpressionParser();

        for (int i = 0; i < listOfMistakes.size(); i++) {
            try {
               CommonExpression expression = parser.parse(listOfMistakes.get(i));
               System.out.println(expression.toMiniString());
            } catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }


    }
}
