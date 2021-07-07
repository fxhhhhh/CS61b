package deque;

public class LinkedListDeque<T> implements Deque<T> {
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

    private LinkedListDeque(T a) {
        size = 1;
        sentinel = new IntNode();
        sentinel.next = new IntNode(a);
        sentinel.next.next = sentinel;
    }

    private LinkedListDeque(IntNode a) {
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
    public void addFirst(T item) {
        IntNode temp = new IntNode((T) item);
        if (size == 0) {
            sentinel.next = temp;
            temp.pre = sentinel;
            sentinel.pre = temp;
            temp.next = sentinel;
            size += 1;
        } else {
            temp.next = sentinel.next;
            temp.pre = sentinel;
            sentinel.next.pre = temp;
            sentinel.next = temp;
            size += 1;
        }
    }

    @Override
    public void addLast(T item) {
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
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T a = null;
        a = this.get(0);
        if (isEmpty()) {
            return a;
        } else {
            sentinel.next.next.pre = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return a;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T a = this.get(size - 1);
        if (isEmpty()) {
            return a;
        } else {
            sentinel.pre = sentinel.pre.pre;
            sentinel.pre.next = sentinel;
            size -= 1;
            return a;
        }
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        } else {
            IntNode ptr = sentinel.next;
            for (int i = 0; i < index; i++) {
                ptr = ptr.next;
            }
            return (T) ptr.item;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;

        if (size != other.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!other.get(i).equals(get(i))) {
                return false;
            }
        }
        return true;
    }
}
