package functions;

public class TermList {
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

    public TermList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Adds a function as the outermost function to the TermList.
     * @param func: A function which will be the new outermost function in the Term.
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
}
