
import org.junit.Test;

import static org.junit.Assert.*;

class SimpleNameMapTest {

    @Test
    void size() {
        SimpleNameMap testMap=new SimpleNameMap();
        assertEquals(0,testMap.size());
    }

    @Test
    void containsKey() {
        SimpleNameMap testMap=new SimpleNameMap();
        java.lang.String a ="abc";
        testMap.put(a,a);

    }

    @Test
    void get() {
    }

    @Test
    void put() {
    }

    @Test
    void remove() {

    }
}