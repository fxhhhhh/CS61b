package deque;

import org.junit.Test;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    @Test
    public void testMax() {
        Comparator<Integer> c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(c);
        for (int i = 0; i < 5; i++) {
            mad.addFirst(i);
        }
        mad.addFirst(-1);
        assertEquals(4, (int) mad.max());
    }

    @Test
    public void testMaxWithOtherComparator() {

    }
}