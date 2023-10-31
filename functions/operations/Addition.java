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

    @Override
    public String toString() {
        if (this.functions.isEmpty()) return "";

        final StringBuilder sb = new StringBuilder();

        String nextFunc = this.functions.get(0).toString();
        for (int i = 0; i < this.functions.size(); i++) {
            // Enter the current function's toString:
            sb.append(nextFunc);

            // Load the toString of the next func:
            if (i < this.functions.size() - 1) {
                nextFunc = this.functions.get(i + 1).toString();
                // Add a '+' only if the next function is not negative:
                if (!nextFunc.startsWith("-"))
                    sb.append(" + ");
            }
        }

        return sb.toString();
    }
}
