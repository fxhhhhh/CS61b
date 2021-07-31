import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap<Integer> h = new MinHeap<Integer>();
        h.insert(1);
        h.insert(2);
        h.insert(3);
        assertEquals( h.size(),4);
        assertEquals((int) h.findMin(),1);
        assertEquals((int) h.removeMin(),1);
        assertEquals(h.size(),3);
        assertTrue(h.contains(3));
        h.insert(0);
        assertEquals((int) h.findMin(),0);
    }
}
