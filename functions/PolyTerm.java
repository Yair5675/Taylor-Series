package functions;

/**
 * A single polynomial term. This class represents any function raised to a power and multiplied by a scalar value.
 * Examples:
 *      2.5x^2
 *      3(x + 1)^3
 *      x^0.5 (which is just sqrt(x))
 */
public record PolyTerm(double scalar, double power) implements Function {

    @Override
    public double compute(double x) {
        return this.scalar * Math.pow(x, this.power);
    }

    @Override
    public Function differentiate() {
        // TODO
        return null;
    }
}