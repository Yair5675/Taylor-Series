package functions.operations;

import functions.Function;
import java.util.Arrays;

/**
 * A class that represents the product between multiple functions.
 */
public record Multiplication(Function fx, Function gx) implements Function {

    @Override
    public double compute(double x) {
        // Multiply the results of the two functions together:
        return this.fx.compute(x) * this.gx.compute(x);
    }

    @Override
    public Function differentiate() {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s * %s", this.fx.toString(), this.gx.toString());
    }
}
