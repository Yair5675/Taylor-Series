package functions;

/**
 * A single polynomial term. This class represents any function raised to a power and multiplied by a scalar value.
 * Examples:
 *      2.5x^2
 *      3(x + 1)^3
 *      x^0.5 (which is just sqrt(x))
 * @param scalar
 * @param body
 * @param power
 */
public record PolyTerm<T extends Function & Differentiable>(double scalar, T body, double power)
        implements Function, Differentiable {

    @Override
    public double compute(double x) {
        return this.scalar * Math.pow(this.body.compute(x), this.power);
    }

    @Override
    public <F extends Function & Differentiable> F differentiate() {
        // TODO
        return null;
    }
}