package functions.operations;

import functions.Function;

/**
 * A class that represents subtraction of any two functions.
 */
public record Subtraction(Function fx, Function gx) implements Function {

    @Override
    public double compute(double x) {
        return fx.compute(x) - gx.compute(x);
    }

    @Override
    public Function differentiate() {
        // TODO
        return null;
    }
}
