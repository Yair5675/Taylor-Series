package functions;

import functions.operations.Multiplication;

import java.util.LinkedList;

public class Term implements Function {
    // The outermost function in the term:
    private TermNode head;

    // The innermost function in the term:
    private TermNode tail;

    /**
     * A class to represent a function within another function and to serve as a node in the overall function chain.
     */
    private static class TermNode {
        // The function of the current node:
        private final Function func;

        // The next function (which is the function within the current function:
        private TermNode next;

        public TermNode(Function func, TermNode next) {
            this.func = func;
            this.next = next;
        }

        /**
         * Computes the value of all the functions from this node onwards using recursion.
         * @param x A parameter for the innermost function which will be propagated up.
         * @return The result of the function of this node when given the result of the function from the next node.
         */
        public double compute(double x) {
            return this.func.compute(this.next == null ? x : this.next.compute(x));
        }

        /**
         * Receives a Function which represents a derivative of an outer function. The function will return a Term
         * object which include said function as the head (the outermost function), and the current TermNode (and those
         * after it) will serve as inner functions inside the outer one.
         * @param derivative A function which represents an outer derivative who needs the current TermNode as an inner
         *                   derivative.
         * @return A Term object that represents a complex function made of the given function as an outer function, and
         *         the rest of the TermNodes chain as inner functions.
         */
        public Term connectAsDerivative(Function derivative) {
            // Initializing the complex function:
            Term complexFunc = new Term();

            // Connecting the derivative as the head of the complex function:
            complexFunc.append_start(derivative);

            // Connecting the complex function's head to the current node:
            complexFunc.head.next = this;

            // Connecting the tail of the complex function to the end of the current chain:
            TermNode pointer = this;
            while (pointer.next != null) {
                pointer = pointer.next;
            }
            complexFunc.tail = pointer;

            return complexFunc;
        }
    }

    public Term() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Adds a function as the outermost function to the Term.
     * @param func A function which will be the new outermost function in the Term.
     */
    public void append_start(Function func) {
        // If there is no head (by that check there isn't a tail either):
        if (this.head == null) {
            this.head = new TermNode(func, null);
            this.tail = this.head;
        }
        // If there is, connect it to the start:
        else {
            this.head = new TermNode(func, this.head);
        }
    }

    /**
     * Adds a function as the innermost function to the Term.
     * @param func A function which will be the new innermost function in the Term.
     */
    public void append_end(Function func) {
        // If there is no tail (by that check there isn't a head either):
        if (this.tail == null) {
            this.tail = new TermNode(func, null);
            this.head = this.tail;
        }
        // If there is, connect it to the end:
        else {
            final TermNode new_tail = new TermNode(func, null);
            this.tail.next = new_tail;
            this.tail = new_tail;
        }
    }

    @Override
    public double compute(double x) {
        return this.head.compute(x);
    }

    @Override
    public Function differentiate() {
        // Storing the functions calculated by the chain function in a linked list until we multiply them:
        final LinkedList<Function> functions = new LinkedList<>();

        // Iterating over the function chain from the outermost function to the innermost:
        TermNode pointer = this.head;
        while (pointer != null) {
            // Getting the derivative for the particular node:
            final Function derivative = pointer.func.differentiate();

            // Since the derivative of the outer function must apply to the inner function, we need to connect the
            // derivative of the current node to the next function in the chain:
            if (pointer.next != null) {
                functions.addLast(pointer.next.connectAsDerivative(derivative));
            }
            else {
                functions.addLast(derivative);
            }

            // Move to the next function:
            pointer = pointer.next;
        }

        // If not functions were produced, simply return f'(x) = x:
        if (functions.isEmpty())
            return new PolyTerm(1, 1);
        // If only one function was produced, return it:
        else if (functions.size() == 1)
            return functions.get(0);
        // If multiple functions were produced, multiply them together:
        Multiplication multiplication = new Multiplication(functions.removeFirst(), functions.removeFirst());
        while (!functions.isEmpty())
            multiplication = new Multiplication(multiplication, functions.removeFirst());

        return multiplication;
    }

    @Override
    public String toString() {
        // Go from the outermost function to the innermost function (if there are no functions, 'x' will be returned):
        String message = "x";
        TermNode pointer = this.head;
        while (pointer != null) {
            message = message.replace("x", String.format("(%s)", pointer.func.toString()));
            pointer = pointer.next;
        }

        // Remove outermost parenthesis:
        message = message.substring(1, message.length() - 1);

        return message;
    }
}
