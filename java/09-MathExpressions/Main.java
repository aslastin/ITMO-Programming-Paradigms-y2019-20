package HW.HW11.expression;

import HW.HW11.expression.binaryOp.Add;
import HW.HW11.expression.binaryOp.Multiply;
import HW.HW11.expression.binaryOp.Subtract;

public class Main {
    public static void main(String[] args) {
       System.out.println(new Add(new Subtract(new Multiply(new Variable("x"), new Variable("x")),
              new Multiply(new Const(2), new Variable("x"))), new Const(1)).evaluate(Double.parseDouble(args[0])));
    }
}
