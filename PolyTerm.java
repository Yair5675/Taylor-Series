/**
 * A single polynomial term. This class represents any function raised to a power and multiplied by a scalar value.
 * Examples:
 *      2.5x^2
 *      3(x + 1)^3
 *      x^0.5 (which is just sqrt(x))
 * @param scalar
 * @param body
 * @param power
 */
public record PolyTerm(double scalar, Function body, double power) {

}