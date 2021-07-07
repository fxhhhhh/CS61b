package deque;

public class ArrayDeque<T> implements Deque <T>{
    private int nowSize = 8;
    private T[] a = (T[]) new Object[nowSize];
    private int size = 0;
    private int nextfirst = 4;
    private int nextlast = 5;
    private T[] array;

    public ArrayDeque() {
        T[] a = (T[]) new Object[8];
        int size = 0;
        int nextfirst = 4;
        int nextlast = 5;
    }

    @Override
    public void addFirst(T item) {
        if (size < nowSize) {
            if (nextfirst != 0) {
                a[nextfirst] = (T) item;
                nextfirst -= 1;
            } else {
                a[nextfirst] = (T) item;
                nextfirst = nowSize - 1;
            }

        } else {
            nowSize = nowSize * 2;
            T[] b = (T[]) new Object[nowSize];
            int index = nextlast;
            for (int i = 0; i < nowSize / 2; i++) {
                if (index > nowSize / 2 - 1) {
                    index = 0;
                }
                b[i] = a[index];
                index += 1;
            }
            a = b;
            nextfirst = nowSize - 1;
            nextlast = size;
            a[nextfirst] = (T) item;
            nextfirst -= 1;
        }
        size += 1;

    }

    @Override
    public void addLast(T item) {
        if (size < nowSize) {
            if (nextlast != nowSize - 1) {
                a[nextlast] = (T) item;
                nextlast += 1;
            } else {
                a[nextlast] = (T) item;
                nextlast = 0;
            }
        } else {
            nowSize = nowSize * 2;
            T[] b = (T[]) new Object[nowSize];
            int index = nextlast;
            for (int i = 0; i < nowSize / 2; i++) {
                if (index > nowSize / 2 - 1) {
                    index = 0;
                }
                b[i] = a[index];
                index += 1;
            }
            a = b;
            nextfirst = nowSize - 1;
            nextlast = nowSize / 2;
            a[nextlast] = (T) item;
            nextlast += 1;
        }
        size += 1;

    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = nextfirst; i < nextlast; i++) {
            System.out.println(a[nextfirst + 1] + " ");
        }
        System.out.println(a[nextlast - 1]);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T temp = (T) get(0);
        if (nextfirst == nowSize - 1) {
            a[0] = null;
            nextfirst = 0;
        } else {
            a[nextfirst + 1] = null;
            nextfirst += 1;
        }
        size -= 1;
        if (size < 0.25 * nowSize) {
            T[] b = (T[]) new Object[nowSize / 2];
            for (int i = 0; i < size; i++) {
                b[i] = (T) this.get(i);
            }
            a = b;
            nowSize = nowSize / 2;
            nextfirst = nowSize - 1;
            nextlast = size;
        }
        return temp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T temp = (T) get(size - 1);
        if (nextlast == 0) {
            a[nowSize - 1] = null;
            nextlast = nowSize;
        } else {
            a[nextlast - 1] = null;
            nextlast -= 1;
        }
        size -= 1;
        if (size < 0.25 * nowSize) {
            int lenth = nowSize / 2;
            T[] b = (T[]) new Object[lenth];
            for (int i = 0; i < size; i++) {
                b[i] = (T) this.get(i);
            }
            nowSize = nowSize / 2;
            a = b;
            nextfirst = nowSize - 1;
            nextlast = size;
        }
        return temp;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1 || size == 0) {
            return null;
        }
        if (nextfirst + 1 + index < nowSize) {
            return a[nextfirst + 1 + index];
        } else {
            return a[nextfirst + 1 + index - nowSize];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() == o.getClass()) {
            ArrayDeque ad = (ArrayDeque) o;
            if (ad.size != this.size) {
                return false;
            }
            for (int i = 0; i < ad.size; i++) {
                T a = (T) this.get(i);
                T b = (T) ad.get(i);
                if (!a.equals(b)) {
                    return false;
                }
            }
            return true;
        } else {
            LinkedListDeque<Integer> lld = (LinkedListDeque) o;
            for (int i = 0; i < lld.size(); i++) {
                T a = (T) this.get(i);
                T b = (T) lld.get(i);
                if (!a.equals(b)) {
                    return false;
                }
            }
            return true;
        }

    }
}


//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if(o==null) return false;
//        Deque<T> other = (Deque<T>) o;
//        if(size!=((Deque<?>) o).size()){
//            return false;
//        }
//        for (int i =0;i<size;i++){
//            if(!other.get(i).equals(get(i))){
//                return false;
//            }
//        }
//        return true;
//    }
