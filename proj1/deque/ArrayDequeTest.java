package deque;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    //public static Deque<Integer> ad = new ArrayDequeTest<Integer>();
//    public static Deque<Integer> ad = new ArrayDeque<>();
    @Test
    public void testAddFirst() {
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(1);
        assertFalse(ad.isEmpty());
        assertEquals(1, ad.size());
        assertEquals(1, (int) ad.get(0));
        ad.addFirst(2);
        assertEquals(2, ad.size());
        assertEquals(2, (int) ad.get(0));
        ad.addFirst(3);
        assertEquals(3, (int) ad.get(0));
    }

    @Test
    public void testAddLast() {
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addLast(1);
        assertFalse(ad.isEmpty());
        assertEquals(1, ad.size());
        assertEquals(1, (int) ad.get(0));
        ad.addLast(2);
        assertEquals(2, ad.size());
        assertEquals(2, (int) ad.get(1));
        ad.addLast(3);
        assertEquals(3, (int) ad.get(2));
    }

    @Test
    public void testIsEmpty() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());
        ad.addFirst(1);
        ad.addLast(2);
        ad.addFirst(3);
        ad.addLast(4);
        for (int i = 0; i < 4; i++) {
            assertFalse(ad.isEmpty());
            ad.removeFirst();
        }
        assertTrue(ad.isEmpty());
    }

    @Test
    public void testSize() {
        Deque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            assertEquals(i, ad.size());
            ad.addFirst(0);
        }
        for (int i = 10; i > 0; i--) {
            assertEquals(i, ad.size());
            ad.removeLast();
        }
        assertEquals(0, ad.size());
    }

    @Test
    public void testRemoveFirst() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertNull(ad.removeFirst());
        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, (int) ad.removeFirst());
        }
        assertNull(ad.removeFirst());
    }

    @Test
    public void testRemoveLast() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertNull(ad.removeLast());
        for (int i = 0; i < 100; i++) {
            ad.addFirst(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, (int) ad.removeLast());
        }
        assertNull(ad.removeLast());
    }

    @Test
    public void testGet() {
        Deque<Integer> ad = new ArrayDeque<>();
        assertNull(ad.get(0));
        assertNull(ad.get(-1));
        assertNull(ad.get(1));
        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, (int) ad.get(i));
        }
        assertNull(ad.get(100));
        assertNull(ad.get(-1));
    }

    @Test
    public void testEquals() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        Deque<Integer> ad2 = new ArrayDeque<>();
        assertEquals(ad1, ad2);
        ad1.addLast(1);
        ad2.addFirst(1);
        assertEquals(ad1, ad2);
        ad1.addFirst(2);
        ad2.addLast(0);
        assertNotEquals(ad1, ad2);
        ad1.removeFirst();
        ad2.removeLast();
        assertEquals(ad1, ad2);
        ad1.addLast(1);
        assertNotEquals(ad1, ad2);
        assertNotEquals(1, ad1);
        assertNotEquals("hello", ad1);
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(1);
        lld1.addLast(1);
        assertEquals(lld1, ad1);
        ad2 = ad1;
        assertEquals(ad1, ad2);
        Deque<Deque> ad3 = new ArrayDeque<>();
        Deque<Deque> ad4 = new ArrayDeque<>();
        ad3.addLast(lld1);
        ad4.addFirst(ad1);
        assertEquals(ad3, ad4);
    }
}