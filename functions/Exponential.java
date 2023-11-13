package functions;

import functions.interfaces.Function;

public class Exponential implements Function {
    @Override
    public double compute(double x) {
        return Math.exp(x);
    }

    @Override
    public Function differentiate() {
        return this;
    }

    @Override
    public String toString() {
        return "e^x";
    }
}
