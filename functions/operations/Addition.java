package functions.operations;

import functions.Function;


/**
 * A class that represents addition of any two functions.
 */
public record Addition(Function fx, Function gx) implements Function {

    @Override
    public double compute(double x) {
        return fx.compute(x) + gx.compute(x);
    }
}
