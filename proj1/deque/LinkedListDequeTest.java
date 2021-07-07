
package deque;

import org.junit.Test;

import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    @Test
    public void testAddFirst() {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(1);
        assertFalse(lld.isEmpty());
        assertEquals(1, lld.size());
        assertEquals(1, (int) lld.get(0));
        lld.addFirst(2);
        assertEquals(2, lld.size());
        assertEquals(2, (int) lld.get(0));
        lld.addFirst(3);
        assertEquals(3, (int) lld.get(0));
    }

    @Test
    public void testAddLast() {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(1);
        assertFalse(lld.isEmpty());
        assertEquals(1, lld.size());
        assertEquals(1, (int) lld.get(0));
        lld.addLast(2);
        assertEquals(2, lld.size());
        assertEquals(2, (int) lld.get(1));
        lld.addLast(3);
        assertEquals(3, (int) lld.get(2));
    }

    @Test
    public void testIsEmpty() {
        Deque<Integer> lld = new LinkedListDeque<>();
        assertTrue(lld.isEmpty());
        lld.addFirst(1);
        lld.addLast(2);
        lld.addFirst(3);
        lld.addLast(4);
        for (int i = 0; i < 4; i++) {
            assertFalse(lld.isEmpty());
            lld.removeFirst();
        }
        assertTrue(lld.isEmpty());
    }

    @Test
    public void testSize() {
        Deque<Integer> lld = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            assertEquals(i, lld.size());
            lld.addFirst(0);
        }
        for (int i = 10; i > 0; i--) {
            assertEquals(i, lld.size());
            lld.removeLast();
        }
        assertEquals(0, lld.size());
    }

    @Test
    public void testRemoveFirst() {
        Deque<Integer> lld = new LinkedListDeque<>();
        assertNull(lld.removeFirst());
        for (int i = 0; i < 5; i++) {
            lld.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) lld.removeFirst());
        }
        assertNull(lld.removeFirst());
    }

    @Test
    public void testRemoveLast() {
        Deque<Integer> lld = new LinkedListDeque<>();
        assertNull(lld.removeLast());
        for (int i = 0; i < 5; i++) {
            lld.addFirst(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) lld.removeLast());
        }
        assertNull(lld.removeLast());
    }

    @Test
    public void testGet() {
        Deque<Integer> lld = new LinkedListDeque<>();
        assertNull(lld.get(0));
        assertNull(lld.get(-1));
        assertNull(lld.get(1));
        for (int i = 0; i < 5; i++) {
            lld.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) lld.get(i));
        }
        assertNull(lld.get(5));
        assertNull(lld.get(-1));
    }

    @Test
    public void testEquals() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertEquals(lld1, lld2);
        lld1.addLast(1);
        lld2.addFirst(1);
        assertEquals(lld1, lld2);
        lld1.addFirst(2);
        lld2.addLast(0);
        assertNotEquals(lld1, lld2);
        lld1.removeFirst();
        lld2.removeLast();
        assertEquals(lld1, lld2);
        lld1.addLast(1);
        assertNotEquals(lld1, lld2);
        assertNotEquals(1, lld1);
        assertNotEquals("hello", lld1);
    }


    @Test
    public void testGetRecursive() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertNull(lld.getRecursive(0));
        assertNull(lld.getRecursive(-1));
        assertNull(lld.getRecursive(1));
        for (int i = 0; i < 5; i++) {
            lld.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) lld.getRecursive(i));
        }
        assertNull(lld.getRecursive(5));
        assertNull(lld.getRecursive(-1));
    }
}