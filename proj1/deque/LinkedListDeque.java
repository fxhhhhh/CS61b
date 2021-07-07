package deque;

public class LinkedListDeque<T> implements Deque {
    private int size;
    private IntNode sentinel;

    private static class IntNode<T> {
        public T item;
        public IntNode next;
        public IntNode pre;

        public IntNode(T item) {
            this.item = item;
            pre = null;
            next = null;
        }

        public IntNode() {
            this.item = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntNode that = (IntNode) o;
            return item == that.item;
        }
    }


    public LinkedListDeque() {
        size = 0;
        sentinel = new IntNode();
        sentinel.next = null;
        sentinel.pre = null;
    }

    public LinkedListDeque(T a) {
        size = 1;
        sentinel = new IntNode();
        sentinel.next = new IntNode(a);
        sentinel.next.next = sentinel;
    }

    public LinkedListDeque(IntNode a) {
        size = 1;
        sentinel = new IntNode();
        sentinel.next = a;
    }

    public T getRecursive(int index) {
        if (size == 0 || index < 0) {
            return null;
        }
        if (index == 0) {
            return (T) sentinel.next.item;
        } else {
            LinkedListDeque a = new LinkedListDeque(sentinel.next.next);
            return (T) a.getRecursive(index - 1);
        }
    }

    @Override
    public void addFirst(Object item) {
        IntNode temp = new IntNode((T) item);
        if (size == 0) {
            sentinel.next = temp;
            temp.pre = sentinel;
            sentinel.pre = temp;
            temp.next = sentinel;
            size += 1;
        } else {
            sentinel.next.pre = temp;
            temp.next = sentinel.next;
            temp.pre = sentinel;
            sentinel.next = temp;
            size += 1;
        }
    }

    @Override
    public void addLast(Object item) {
        IntNode temp = new IntNode((T) item);
        if (size == 0) {
            sentinel.next = temp;
            temp.pre = sentinel;
            sentinel.pre = temp;
            temp.next = sentinel;
            size += 1;
        } else {
            sentinel.pre.next = temp;
            temp.pre = sentinel.pre;
            temp.next = sentinel;
            sentinel.pre.next = temp;
            sentinel.pre = temp;
            size += 1;
        }
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        IntNode ptr = sentinel;
        while (ptr.next != sentinel) {
            System.out.println(ptr.item + " ");
        }
        System.out.println(ptr);
    }

    @Override
    public Object removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T a = null;
        a = (T) sentinel.next.item;
        if (isEmpty()) {
            return a;
        } else {
            sentinel.next = sentinel.next.next;
            sentinel.next.pre = sentinel;
            return a;
        }
    }

    @Override
    public Object removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T a = null;
        a = (T) sentinel.pre.item;
        if (isEmpty()) {
            return a;
        } else {
            sentinel.pre = sentinel.pre.pre;
            sentinel.pre.pre.next = sentinel;
            return a;
        }
    }

    @Override
    public Object get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        } else {
            IntNode ptr = sentinel.next;
            for (int i = 0; i < index; i++) {
                ptr = ptr.next;
            }
            return ptr.item;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else {
            if (getClass() == o.getClass()) {
                LinkedListDeque slList = (LinkedListDeque) o;
                if (this.size == 0 && slList.size == 0) return true;
                if (size != slList.size) return false;
                IntNode l1 = sentinel.next;
                IntNode l2 = slList.sentinel.next;

                while (l1 != sentinel && l2 != slList.sentinel) {
                    if (!l1.equals(l2)) return false;
                    l1 = l1.next;
                    l2 = l2.next;
                }
                return true;
            } else {
                ArrayDeque ad = (ArrayDeque) o;
                for (int i = 0; i < ad.size(); i++) {
                    T a = (T) this.get(i);
                    T b = (T) ad.get(i);
                    if (a != b) {
                        return false;
                    }
                    return true;
                }
            }
            return true;
        }
    }
}
