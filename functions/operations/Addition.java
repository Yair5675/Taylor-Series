package functions.operations;

import functions.Function;

import java.util.ArrayList;
import java.util.List;


/**
 * A class that represents addition of multiple functions.
 */
public class Addition implements Function {
    // The functions which are summed up:
    private final ArrayList<Function> functions;

    public Addition(Function ... functions) {
        this.functions = new ArrayList<>(List.of(functions));
    }

    @Override
    public double compute(double x) {
        // Sum the results of calling 'compute' on every function:
        return this.functions.stream().mapToDouble((function -> function.compute(x))).sum();
    }

    @Override
    public Function differentiate() {
        // The derivative of a sum is the sum of derivatives:
        return new Addition(this.functions.stream().map(Function::differentiate).toArray(Function[]::new));
    }
}
