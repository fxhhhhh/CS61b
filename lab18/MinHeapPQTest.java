import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void testSize() {
        MinHeapPQ test=new MinHeapPQ<>();
        int size = test.size();
        assertEquals(size,0);

    }
    @Test
    public void testInsertPoll(){
        MinHeapPQ test=new MinHeapPQ<>();
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
    @Test
    public void testPriorty(){
        MinHeapPQ test=new MinHeapPQ<>();
        for (int i=1;i<10;i++)
        {
            test.insert(10-i,10-i);
        }
        test.changePriority(1,10);
        test.changePriority(9,1);
        test.insert(11,11);


    }
}
