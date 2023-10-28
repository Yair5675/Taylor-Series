package functions;

public interface Differentiable {
    <T extends Function & Differentiable> T differentiate();
}
