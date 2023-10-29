package functions;

import functions.operations.Multiplication;

import java.util.LinkedList;

public class Term implements Function {
    // The outermost function in the term:
    private TermNode head;

    // The innermost function in the term:
    private TermNode tail;

    // The number o derivative this term is (0 if it was the original function, 1 for the first derivative and so on):
    private int derivativeNum;

    /**
     * A class to represent a function within another function and to serve as a node in the overall function chain.
     */
    private static class TermNode {
        // The previous function (in which the current function is):
        private TermNode prev;

        // The function of the current node:
        private Function func;

        // The next function (which is the function within the current function:
        private TermNode next;

        // Since the current node might be used by derivatives as well, it will hold a list where each index corresponds
        // to a derivative number (index 0 is connected to the first derivative, index 1 to the second and so on):
        private LinkedList<TermNode> connectedDerivatives;

        public TermNode(TermNode prev, Function func, TermNode next) {
            this.prev = prev;
            this.func = func;
            this.next = next;
            this.connectedDerivatives = new LinkedList<>();
        }

        /**
         * Receives a Function which represents a derivative of an outer function. The function will return a Term
         * object which include said function as the head (the outermost function), and the current TermNode (and those
         * after it) will serve as inner functions inside the outer one.
         * @param derivative A function which represents an outer derivative who needs the current TermNode as an inner
         *                   derivative.
         * @param derivativeNum The number of differentiation that resulted in the given function. This is necessary to
         *                      place the head of the returned complex function in the correct index of the
         *                      'connectedDerivatives' list.
         * @return A Term object that represents a complex function made of the given function as an outer function, and
         *         the rest of the TermNodes chain as inner functions.
         */
        public Term connectAsDerivative(Function derivative, int derivativeNum) {
            // initializing the complex function:
            Term complexFunc = new Term(derivativeNum);

            // Connecting the derivative as the head of the complex function:
            complexFunc.append_start(derivative);

            // Connecting the head to the current TermNode (needed when computing a value for the derivative):
            this.connectedDerivatives.add(derivativeNum - 1, complexFunc.head);

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
        this.derivativeNum = 0;
    }

    private Term(int derivativeNum) {
        this();
        this.derivativeNum = derivativeNum;
    }

    /**
     * Adds a function as the outermost function to the Term.
     * @param func A function which will be the new outermost function in the Term.
     */
    public void append_start(Function func) {
        // If there is no head (by that check there isn't a tail either):
        if (this.head == null) {
            this.head = new TermNode(null, func, null);
            this.tail = this.head;
        }
        // If there is, connect it to the start:
        else {
            final TermNode new_head = new TermNode(null, func, this.head);
            this.head.prev = new_head;
            this.head = new_head;
        }
    }

    /**
     * Adds a function as the innermost function to the Term.
     * @param func A function which will be the new innermost function in the Term.
     */
    public void append_end(Function func) {
        // If there is no tail (by that check there isn't a head either):
        if (this.tail == null) {
            this.tail = new TermNode(null, func, null);
            this.head = this.tail;
        }
        // If there is, connect it to the end:
        else {
            final TermNode new_tail = new TermNode(this.tail, func, null);
            this.tail.next = new_tail;
            this.tail = new_tail;
        }
    }

    @Override
    public double compute(double x) {
        if (this.tail != null) {
            // Going from the innermost function outwards to compute the term:
            TermNode pointer = this.tail;
            while (pointer != null) {
                x = pointer.func.compute(x);
                pointer = pointer.prev;
            }

            return x;
        }
        else {
            return 0;
        }
    }

    @Override
    public Function differentiate() {
        // TODO
        return null;
    }
}
