import org.junit.Test;

import static org.junit.Assert.*;

public class ModNCounterTest {

    @Test
    public void testConstructor() {
        ModNCounter c = new ModNCounter(3);
        assertEquals(0, c.value());
    }

    @Test
    public void testIncrement() throws Exception {
        ModNCounter c = new ModNCounter(3);
        c.increment();
        assertEquals(1, c.value());
        c.increment();
        assertEquals(2, c.value());
        c.increment();
        assertEquals(0, c.value());
    }

    @Test
    public void testReset() throws Exception {
        ModNCounter c = new ModNCounter(3);
        c.increment();
        c.reset();
        assertEquals(0, c.value());
    }
}