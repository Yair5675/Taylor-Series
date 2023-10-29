package functions;

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
