package functions.operations;

import functions.Function;
import java.util.Arrays;


/**
 * A class that represents addition of multiple functions.
 */
public record Addition(Function[] functions) implements Function {

    @Override
    public double compute(double x) {
        // Sum the results of calling 'compute' on every function:
        return Arrays.stream(this.functions).mapToDouble((function -> function.compute(x))).sum();
    }
}
