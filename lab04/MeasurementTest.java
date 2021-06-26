import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurementTest {
    @Test
    // TODO: Add additional JUnit tests for Measurement.java here.
    public void test1() {
        // TODO: stub for first test
        Measurement a =new  Measurement();
        assertEquals(0,a.getFeet());
        assertEquals(0,a.getInches());
    }
    @Test
    public void test2(){
        // TODO: stub for first test
        Measurement a =new  Measurement(1);
        assertEquals(1,a.getFeet());
        assertEquals(0,a.getInches());
    }
    @Test
    public void test3(){
        // TODO: stub for first test
        Measurement a =new  Measurement(1,2);
        assertEquals(1,a.getFeet());
        assertEquals(2,a.getInches());
    }
    @Test
    public void test4() {
        // TODO: stub for first test
        Measurement a =new  Measurement(1,2);
        Measurement b =new  Measurement(3,4);
        Measurement result=a.plus(b);
        assertEquals(4,result.getFeet());
        assertEquals(6,result.getInches());
    }
    @Test
    public void test5() {
        // TODO: stub for first test
        Measurement a =new  Measurement(1,2);
        Measurement b =new  Measurement(3,4);
        Measurement result=b.minus(a);
        assertEquals(2,result.getFeet());
        assertEquals(2,result.getInches());
    }
    @Test
    public void test6() {
        // TODO: stub for first test
        Measurement a =new  Measurement(1,2);
        a=a.multiple(0);
        assertEquals(0,a.getFeet());
        assertEquals(0,a.getInches());
    }
    @Test
    public void test7() {
        // TODO: stub for first test
        Measurement a =new  Measurement(1,2);
        assertEquals("1'2\"",a.toString());
    }

}