package functions.operations;

import functions.interfaces.Function;

/**
 * A record that represents the product between multiple functions.
 */
public record Multiplication(Function fx, Function gx) implements Function {

    @Override
    public double compute(double x) {
        // Multiply the results of the two functions together:
        return this.fx.compute(x) * this.gx.compute(x);
    }

    @Override
    public Function differentiate() {
        // Differentiate according to the product rule:
        return new Addition(
                new Multiplication(this.fx.differentiate(), this.gx),
                new Multiplication(this.gx.differentiate(), this.fx)
        );
    }

    @Override
    public String toString() {
        return String.format("%s * %s", this.fx.toString(), this.gx.toString());
    }
}
