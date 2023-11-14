package functions;

import functions.interfaces.Function;
import functions.operations.Addition;

import java.util.LinkedList;
import java.util.List;

/**
 * A single polynomial term. This class represents any function raised to a power and multiplied by a scalar value.
 * Examples:
 *      2.5x^2
 *      3(x + 1)^3
 *      x^0.5 (which is just sqrt(x))
 */
public class PolyTerm implements Function{
    // The scalar of the polynomial term:
    private double scalar;

    // The power of the polynomial term:
    private double power;

    public PolyTerm(double scalar, double power) {
        this.scalar = scalar;
        this.power = power;
    }

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

    public static Function sum(PolyTerm ... terms) {
        if (terms.length == 0)
            return new PolyTerm(0, 0);

        final LinkedList<PolyTerm> summedPolynomials = new LinkedList<>(List.of(terms[0]));
        for (int i = 1; i < terms.length; i++) {
            boolean simplified = false;
            final PolyTerm f = summedPolynomials.get(i);

            for (final PolyTerm g : summedPolynomials) {
                if (f.power == g.power) {
                    g.scalar += f.scalar;
                    simplified = true;
                    break;
                }
            }

            if (!simplified)
                summedPolynomials.addLast(f);
        }

        return new Addition(summedPolynomials.toArray(Function[]::new));
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

    public double power() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double scalar() {
        return scalar;
    }

    public void setScalar(double scalar) {
        this.scalar = scalar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (obj instanceof PolyTerm other)
            return this.scalar == other.scalar && this.power == other.power;

        else if (obj instanceof Complex other) {
            if (other.size() == 1)
                if (other.getFunctionAt(0) instanceof PolyTerm polyTerm)
                    return this.scalar == polyTerm.scalar && this.power == polyTerm.power;
        }

        return false;
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