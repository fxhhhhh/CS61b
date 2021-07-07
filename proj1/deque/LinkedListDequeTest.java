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

    //public static Deque<Integer> lld = new LinkedListDeque<Integer>();


//    @Test
//    /** Adds a few things to the list, checks that isEmpty() is correct.
//     * This is one simple test to remind you how junit tests work. You
//     * should write more tests of your own.
//     *
//     * && is the "and" operation. */
//    public void addIsEmptySizeTest() {
//
//        System.out.println("Make sure to uncomment the lines below (and delete this print statement)." +
//                " The code you submit to the AG shouldn't have any print statements!");
//        /*
//
//		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
//		lld.addFirst(0);
//
//        assertFalse("lld should now contain 1 item", lld.isEmpty());
//
//        // Reset the linked list deque at the END of the test.
//        lld = new LinkedListDeque<Integer>();
//		*/
//    }
    @Test
    public void testAddFirst() {
        Deque<Integer> lld = new LinkedListDeque<>();
        for ( int i=0;i<500;i++){
            lld.addFirst(i);
        }
        for ( int i=0;i<500;i++){
            int temp=lld.removeFirst();
            assertEquals(499-i,temp);
        }
        assertTrue(lld.isEmpty());
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
        for (int i = 0; i < 100; i++) {
            lld.addLast(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, (int) lld.get(i));
        }
        assertNull(lld.get(100));
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
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(1);
        ad1.addLast(1);
        assertEquals(ad1, lld1);
        lld2 = lld1;
        assertEquals(lld1, lld2);
        Deque<Deque> lld3 = new LinkedListDeque<>();
        Deque<Deque> lld4 = new LinkedListDeque<>();
        lld3.addLast(lld1);
        lld4.addFirst(lld2);
        assertEquals(lld3, lld4);
    }


    @Test
    public void testGetRecursive() {
        LinkedListDeque<Integer> ad = new LinkedListDeque<>();
        assertNull(ad.getRecursive(0));
        assertNull(ad.getRecursive(-1));
        assertNull(ad.getRecursive(1));
        for (int i = 0; i < 5; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, (int) ad.getRecursive(i));
        }
        assertNull(ad.getRecursive(5));
        assertNull(ad.getRecursive(-1));
    }
    @Test
    public void agTest() {
        Deque<Integer> LinkedListDeque = new LinkedListDeque<Integer>();
        LinkedListDeque.addLast(0);
        LinkedListDeque.removeFirst()   ;
        LinkedListDeque.addLast(2);
        LinkedListDeque.addFirst(3);
        LinkedListDeque.removeLast()   ;
        int a =LinkedListDeque.removeFirst();
        assertEquals(3,a);
    }
}