package functions;

import functions.interfaces.Function;

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

    /**
     * Given two polynomial terms, the function returns the product of the two terms. This function should be used when
     * simplifying terms is needed. The function does not affect the two terms it receives, only creates a new one.
     * @param a Any polynomial term, including a constant.
     * @param b Any polynomial term, including a constant.
     * @return The product of a and b.
     */
    public static PolyTerm multiply(PolyTerm a, PolyTerm b) {
        return new PolyTerm(a.scalar * b.scalar, a.power + b.power);
    }

    @Override
    public String toString() {
        // If the scalar is zero, return zero:
        if (this.scalar == 0) return "0";

        // Removing the tailing zeroes from the scalar and the power:
        String scalar = Double.toString(this.scalar).replaceAll("0*?", "");
        String power = Double.toString(this.power).replaceAll("0*?", "");

        // If the scalar or power are actually integers:
        scalar = this.scalar == (int) this.scalar ? Integer.toString((int) this.scalar) : scalar;
        power = this.power == (int) this.power ? Integer.toString((int) this.power) : power;

        // If the power is 0, this is a constant:
        if (this.power == 0)
            return scalar;

        // If the scalar or power is equal to one:
        if (this.scalar == 1)
            scalar = "";
        if (this.power == 1)
            power = "";

        // Constructing the final toString message:
        if (scalar.isEmpty() && power.isEmpty())
            return "x";
        else if (scalar.isEmpty())
            return String.format("x^%s", power);
        else if (power.isEmpty())
            return String.format("%sx", scalar);
        else
            return String.format("%sx^%s", scalar, power);
    }
}