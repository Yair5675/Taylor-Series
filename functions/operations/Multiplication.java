package functions.operations;

import functions.Function;

/**
 * A class that represents multiplication between two functions.
 */
public record Multiplication(Function fx, Function gx) implements Function {

    @Override
    public double compute(double x) {
        return fx.compute(x) * gx.compute(x);
    }
}
