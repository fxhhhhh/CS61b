import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ test=new MinHeapPQ<Integer>();
        int size = test.size();
        assertEquals(size,0);
        for(int i =0;i<10;i++){
            test.insert(i,i);
        }
        assertEquals(test.peek(),0);
        test.changePriority(0,2);
        assertEquals(test.peek(),1);


    }
}
