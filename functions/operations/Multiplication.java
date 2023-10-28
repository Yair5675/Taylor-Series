package functions.operations;

import functions.Function;
import java.util.Arrays;

/**
 * A class that represents the product between multiple functions.
 */
public record Multiplication(Function[] functions) implements Function {

    @Override
    public double compute(double x) {
        // Multiply all the results of the functions together:
        return Arrays.stream(this.functions)
                .mapToDouble(function -> function.compute(x))
                .reduce(1.0, (accumulator, element) -> accumulator * element);
    }
}
