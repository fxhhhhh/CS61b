package deque;

import javax.swing.*;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque {
    private Comparator<T> c;

    public MaxArrayDeque(Comparator<T> c) {
        this.c = c;
    }

    public T max() {
        return max(c);
    }


    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxT = (T) get(0);
        for (int i = 1; i < size(); i++) {
            if (c.compare(maxT, (T) get(i)) < 0) {
                maxT = (T) get(i);
            }
        }
        return maxT;
    }


}
