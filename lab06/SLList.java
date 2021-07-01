/**
 * An SLList is a list of integers, which encapsulates the
 * naked linked list structure.
 */
public class SLList {

    /**
     * IntListNode is a nested class that represents a single node in the
     * SLList, storing an item and a reference to the next IntListNode.
     */
    private static class IntListNode {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public int item;
        public IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntListNode that = (IntListNode) o;
            return item == that.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntListNode sentinel;
    private int size;

    /**
     * Creates an empty SLList.
     */
    public SLList() {
        sentinel = new IntListNode(42, null);
        sentinel.next = sentinel;
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntListNode(42, null);
        sentinel.next = new IntListNode(x, null);
        sentinel.next.next = sentinel;
        size = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SLList slList = (SLList) o;
        if (size != slList.size) return false;

        IntListNode l1 = sentinel.next;
        IntListNode l2 = slList.sentinel.next;

        while (l1 != sentinel && l2 != slList.sentinel) {
            if (!l1.equals(l2)) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
        return true;
    }

    @Override
    public String toString() {
        IntListNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l + " ";
            l = l.next;
        }

        return result.trim();
    }

    /**
     * Returns an SLList consisting of the given values.
     */
    public static SLList of(int... values) {
        SLList list = new SLList();
        for (int i = values.length - 1; i >= 0; i -= 1) {
            list.addFirst(values[i]);
        }
        return list;
    }

    /**
     * Returns the size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Adds x to the front of the list.
     */
    public void addFirst(int x) {
        sentinel.next = new IntListNode(x, sentinel.next);
        size += 1;
    }

    /**
     * Return the value at the given index.
     */
    public int get(int index) {
        IntListNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /**
     * Adds x to the list at the specified index.
     */
    public void add(int index, int x) {
        // TODO
        IntListNode ptr = sentinel;
        for (int i = 0; i < index; i++) {
            if (ptr.next != sentinel) {
                ptr = ptr.next;
            } else {
                break;
            }
        }
        IntListNode newOne = new IntListNode(x, ptr.next);
        ptr.next = newOne;
        size += 1;
    }

    /**
     * Destructively reverses this list.
     */
//    public void reverse() {
//        // TODO
//        if (sentinel.next == sentinel || sentinel.next.next == sentinel) {
//            return;
//        } else {
//            int item[]=new int[size];
//            IntListNode ptr=sentinel;
//            for (int i = 0; i < size; i++) {
//                item[i]=ptr.next.item;
//                ptr=ptr.next;
//            }
//            IntListNode first=sentinel;
//            this.sentinel.next=first;
//            for (int i = size-1; i >0; i--) {
//                IntListNode temp=new IntListNode(item[i],null);
//                first.next=temp;
//                first=first.next;
//                }
//            first.next=sentinel;
//            }
//        }
//    public void helpFunction(IntListNode L) {
//        // TODO
//        IntListNode temp=null;
//        temp=L.next;
//        temp.next=L;
//    }
//    public void reverse() {
//        // TODO
//        IntListNode a =sentinel.next;
//        for (int i =0;i<size;i++)
//        {
//            helpFunction(a);
//            a=a.next;
//            if(i==0){
//                a.next=sentinel;
//            }
//            sentinel.next=a;
//        }
//
//    }
    public void reverse() {
        // TODO
        if (sentinel.next == null) {
            return;
        }
        IntListNode ptr = sentinel;
        while (ptr.next != sentinel) {
            ptr = ptr.next;
        }
        IntListNode temp = reverseHelper(sentinel.next);
        temp.next = sentinel;
        sentinel.next = ptr;
    }

    public IntListNode reverseHelper(IntListNode startNode) {
        if (startNode.next == sentinel) {
            return startNode;
        }
        IntListNode temp = reverseHelper(startNode.next);
        temp.next = startNode;
        return startNode;
    }
}

