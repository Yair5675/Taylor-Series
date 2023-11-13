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
        derivative.append_start(new PolyTerm(1, -1));
        derivative.append_end(new Multiplication(new PolyTerm(1, 1), new Log()));

        return derivative;
    }
}
