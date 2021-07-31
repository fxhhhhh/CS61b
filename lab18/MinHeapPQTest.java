import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ test=new MinHeapPQ<>();
        int size = test.size();
        assertEquals(size,0);
        test.insert('a',0);
        test.insert('b',1);
        test.insert('c',2);
        test.insert('d',3);
        test.insert('e',4);
        test.insert('f',5);
        assertTrue(test.contains('a'));
        assertFalse(test.contains('g'));
        test.changePriority('a',6);
        System.out.println(test.peek());
        test.changePriority('f',0);
        System.out.println(test.peek());

    }
}
