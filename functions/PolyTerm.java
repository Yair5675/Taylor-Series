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
        // If the current polynomial term is a constant:
        if (this.power == 0)
            return new PolyTerm(0, 0);
        // If not, use the power rule:
        else
            return new PolyTerm(this.scalar * this.power, this.power - 1);
    }

    @Override
    public String toString() {
        return String.format("%fx^(%f)", this.scalar, this.power);
    }
}