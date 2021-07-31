import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ test=new MinHeapPQ<>();
        int size = test.size();
        assertEquals(size,0);
        for (int i=1;i<10;i++)
        {
            test.insert(10-i,10-i);
        }
        MinHeap<Integer> h = new MinHeap<Integer>();
        for (int i=1;i<10;i++)
        {
            h.insert(10-i);
        }
        for (int i=1;i<10;i++)
        {
            assertEquals(test.poll(),h.removeMin());
        }

    }
}
