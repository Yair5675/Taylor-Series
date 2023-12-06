package functions;

import functions.interfaces.Function;
import functions.operations.Multiplication;

public class Log implements Function {
    private double base;

    public Log() {
        this.base = Math.E;
    }

    public Log(double base) {
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
        return Math.log(x) / Math.log(this.base);
    }

    @Override
    public Function differentiate() {
        // Derivative of a log with e as the base is 1 / x:
        if (this.base == Math.E)
            return new PolyTerm(1, -1);

        // Derivative of a log with special base is (x * ln(base)) ^ -1:
        final Complex derivative = new Complex();
        derivative.appendStart(new PolyTerm(1, -1));
        derivative.appendEnd(new Multiplication(new PolyTerm(1, 1), new Log()));

        return derivative;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        else if (this == obj)
            return true;

        else if (obj instanceof Log other)
            return this.base == other.base;

        else if (obj instanceof Complex other)
            if (other.size() == 1)
                if (other.getFunctionAt(0) instanceof Log log)
                    return this.base == log.base;

        return false;
    }

    @Override
    public String toString() {
        if (this.base == Math.E)
            return "ln(x)";

        // Remove trailing zeroes from base:
        String base = Double.toString(this.base).replaceAll("0*?", "");
        base = this.base == (int) this.base ? Integer.toString((int) this.base) : base;

        return String.format("log%s(x)", base);
    }
}
