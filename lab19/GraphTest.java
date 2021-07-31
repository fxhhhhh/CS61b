import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class GraphTest {

    @Test
    public void testALl() {
        Graph a =new Graph(5);
        a.addEdge(0,1);
        a.addEdge(1,2);
        a.addEdge(2,3);
        a.addEdge(3,4);
        a.addEdge(4,0);
        assertTrue(a.isAdjacent(0,1));
        List<Integer> neighbors =new LinkedList();
        neighbors.add(4);
        assertEquals(a.neighbors(0),neighbors);
    }


}