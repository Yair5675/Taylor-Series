package functions;

import functions.interfaces.Function;
import functions.operations.Addition;
import functions.operations.Multiplication;

import java.util.ArrayList;
import java.util.List;

public class Comparator implements java.util.Comparator<Function> {
    // The order will be (from lowest to highest):
    // 1) Polynomial terms (sorted by their power).
    // 2) Exponential terms.
    // 3) Addition.
    // 4) Multiplication.
    // 5) Complex.
    private static final ArrayList<Class<? extends Function>> order = new ArrayList<>(List.of(
            PolyTerm.class,
            Exponential.class,
            Addition.class,
            Multiplication.class,
            Complex.class
    ));

    @Override
    public int compare(Function f, Function g) {
        if (f == null || g == null)
            return 0;

        // If it's the same object, return 0:
        if (f == g)
            return 0;

        // If they are polynomial terms, compare using the function:
        if (f instanceof PolyTerm fp && g instanceof PolyTerm gp)
            return Double.compare(fp.power(), gp.power());

        // Compare using the order list:
        final int fIdx = order.indexOf(f.getClass()), gIdx = order.indexOf(g.getClass());

        // If the class is not present in the order list, return 0:
        if (fIdx == -1 || gIdx == -1)
            return 0;

        // Compare using the indices:
        return Integer.compare(fIdx, gIdx);
    }
}
