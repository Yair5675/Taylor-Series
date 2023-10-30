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

    /**
     * Computes the Term's value assuming the term is not a part of a derivative.
     * @param x The value substituted in the innermost function and then propagated up the term chain.
     * @return The value of the complex function for the given x. If the complex function is empty, x is returned.
     */
    private double computeIfNormal(double x) {
        // Go from the innermost function outwards:
        TermNode pointer = this.tail;
        while (pointer != null) {
            x = pointer.func.compute(x);
            // Go up the chain normally:
            pointer = pointer.prev;
        }

        return x;
    }

    /**
     * Computes the Term's value assuming the term is a part of a derivative and connected to another complex function.
     * @param x The value substituted in the innermost function and then propagated up the term chain.
     * @return The value of the complex function for the given x. If the complex function is empty, x is returned.
     */
    private double computeIfDerivative(double x) {
        // Making sure there is a function:
        if (this.head == null) return x;

        // We need to go from the innermost function outwards. However, each node may be connected to other derivatives.
        // To avoid calculating other derivatives, we will check when the node is connected to the current derivative by
        // checking if it's the second node in the chain (the head is the outer derivative, and the second node is the
        // inner function):
        TermNode pointer = this.tail;
        final TermNode connectedNode = this.head.next;
        // If the derivative of the outer function doesn't have an inner function:
        if (connectedNode == null)
            return this.head.func.compute(x);

        // Go up normally until you reach the connected node:
        while (pointer != null) {
            x = pointer.func.compute(x);
            // If it's the connected node, go up through the derivative index:
            if (pointer == connectedNode)
                pointer = connectedNode.connectedDerivatives.get(this.derivativeNum - 1);
                // If not, advance normally:
            else
                pointer = pointer.prev;
        }
        return x;
    }

    @Override
    public double compute(double x) {
        if (this.derivativeNum == 0)
            return this.computeIfNormal(x);
        else
            return this.computeIfDerivative(x);
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
                functions.addLast(pointer.next.connectAsDerivative(derivative, this.derivativeNum + 1));
            }
            else {
                functions.addLast(derivative);
            }

            // Move to the next function:
            pointer = pointer.next;
        }

        // Once all functions were calculated according to the chain rule, multiply them all together:
        return new Multiplication(functions.toArray(new Function[0]));
    }

    @Override
    public String toString() {
        // Go from the outermost function to the innermost function (if there are no functions, 'x' will be returned):
        String message = "x";
        TermNode pointer = this.head;
        while (pointer != null) {
            message = message.replace("x", pointer.func.toString());
            pointer = pointer.next;
        }

        return message;
    }
}
