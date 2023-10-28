package functions;

public class Term {
    // The outermost function in the term:
    private TermNode head;

    // The innermost function in the term:
    private TermNode tail;

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

        public TermNode(TermNode prev, Function func, TermNode next) {
            this.prev = prev;
            this.func = func;
            this.next = next;
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
}
