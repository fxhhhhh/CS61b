import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap<Integer> h = new MinHeap<Integer>();
        for (int i=1;i<10;i++)
        {
            h.insert(10-i);
        }
        for (int i=1;i<10;i++)
        {
            System.out.println(h.removeMin());
        }
    }
}
