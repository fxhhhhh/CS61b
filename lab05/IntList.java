import java.util.WeakHashMap;

/**
 * A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /**
     * The integer stored by this node.
     */
    public int item;
    /**
     * The next node in this IntList.
     */
    public IntList next;

    /**
     * Constructs an IntList storing ITEM and next node NEXT.
     */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /**
     * Constructs an IntList storing ITEM and no next node.
     */
    public IntList(int item) {
        this(item, null);
    }

    /**
     * Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3
     */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        //TODO: YOUR CODE HERE
        IntList l = this;
        if (position < 0) {
            throw new IllegalArgumentException("YOUR MESSAGE HERE");
        }
        for (int index = 0; index < position; index++) {
            if (l.next == null) {
                throw new IllegalArgumentException("YOUR MESSAGE HERE");
            }
            l = l.next;
        }
        return l.item;
    }


    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        //TODO: YOUR CODE HERE
        IntList ptr = this;
        String str;
        str = String.valueOf(ptr.item);
        while (ptr.next != null) {
            ptr = ptr.next;
            str += " " + ptr.item;
        }
        return str;
    }

    /**
     * Returns whether this and the given list or object are equal.
     * <p>
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }
        IntList otherLst = (IntList) obj;
        //TODO: YOUR CODE HERE
        boolean flag = true;
        if (((IntList) obj).next == this.next && this.item == ((IntList) obj).item && this.next == null) {
            return true;
        }
        if (this.item != ((IntList) obj).item) {
            return false;
        } else {
            return this.next.equals(((IntList) obj).next);
        }
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        //TODO: YOUR CODE HERE
        if (this.next != null) {
            this.next.add(value);
        } else {
            this.next = new IntList(value);
        }
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        //TODO: YOUR CODE HERE
        int small = this.item;
        IntList ptr = this;
        while (ptr.next != null) {
            if (ptr.item < small) {
                small = ptr.item;
            }
            ptr = ptr.next;
        }
        if (ptr.item < small) {
            small = ptr.item;
            ptr = ptr.next;
        }
        return small;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        //TODO: YOUR CODE HERE
        if (this.next == null) {
            return this.item * this.item;
        } else {
            return this.item * this.item + this.next.squaredSum();
        }
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        if(A==null){
            return B;
        }
        IntList result=A;
        while (A.next!=null){
            A=A.next;
        }
        A.next=B;
        return result;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList catenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        if (A.next == null) {
            return B;
        }
        IntList result = new IntList(A.item,null);
        IntList temp=result;
        A=A.next;
        while (A.next != null) {
            temp.next = new IntList(A.item,null);
            A = A.next;
            temp=temp.next;
        }
        temp.next = new IntList(A.item,null);
        temp=temp.next;
        while (B.next != null) {
            temp.next = new IntList(B.item,null);
            B = B.next;
            temp=temp.next;
        }
        temp.next = new IntList(B.item,null);
        return result;

    }


}