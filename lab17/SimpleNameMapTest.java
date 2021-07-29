import org.junit.Test;
import static org.junit.Assert.*;


public class SimpleNameMapTest {

    @Test
    public void size() {
        SimpleNameMap testMap=new SimpleNameMap();
        testMap.put("ABC","ABC");
        testMap.put("ABCD","ABC");
        testMap.put("ABCE","ABC");
        testMap.put("ABCR","ABC");
        testMap.put("ABCT","ABC");
        testMap.put("ABCY","ABC");
        testMap.put("ABCS","ABC");
        testMap.put("ABCJ","ABC");
        testMap.put("ABCV","ABC");
        testMap.put("ABCM","ABC");
        assertEquals(10,testMap.size());
        assertEquals(20,testMap.initialArray.length);
    }

    @Test
    public void containsKey() {
        SimpleNameMap testMap=new SimpleNameMap();
        String a ="Abc";
        testMap.put(a,a);
        String b="Bac";
        testMap.put(b,b);
        String c="Acb";
        testMap.put(c,c);
        assertTrue(testMap.containsKey(a));
        assertTrue(testMap.containsKey(b));
        assertTrue(testMap.containsKey(c));
    }

    @Test
    public void get() {
        SimpleNameMap testMap=new SimpleNameMap();
        String a ="Abc";
        testMap.put(a,a);
        String b="Bac";
        testMap.put(b,b);
        String c="Acb";
        testMap.put(c,c);
        assertEquals(a,testMap.get(a));
        assertEquals(c,testMap.get(c));

    }

    @Test
    public void remove() {
        SimpleNameMap testMap=new SimpleNameMap();
        String a ="Abc";
        testMap.put(a,a);
        String b="Bac";
        testMap.put(b,b);
        String c="Acb";
        testMap.put(c,c);
        assertEquals(3,testMap.size());
        testMap.remove(c);
        assertEquals(2,testMap.size());
        assertEquals(b,testMap.remove(b));
    }
}