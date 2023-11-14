package functions;

import functions.interfaces.Function;
import functions.operations.Multiplication;

public class Exponential implements Function {
    private double base;

    public Exponential() {
        this.base = Math.E;
    }

    public Exponential(double base) {
        this.base = base;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    @Override
    public double compute(double x) {
        return Math.pow(this.base, x);
    }

    @Override
    public Function differentiate() {
        // Derivative of e^x is e^x:
        if (this.base == Math.E)
            return this;

        // Derivative of a^x is a^x * ln(a):
        return new Multiplication(this, new PolyTerm(Math.log(this.base), 0));
    }

    @Override
    public String toString() {
        // Get the base:
        String base = Double.toString(this.base).replaceAll("0*?", "");
        base = this.base == (int) this.base ? Integer.toString((int) this.base) : base;

        return String.format("%s^x", base);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        else if (this == obj)
            return true;

        else if (obj instanceof Exponential other)
            return this.base == other.base;

        else if (obj instanceof Complex other)
            if (other.size() == 1)
                if (other.getFunctionAt(0) instanceof Exponential exp)
                    return this.base == exp.base;

        return false;
    }
}
