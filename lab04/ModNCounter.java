public class ModNCounter {

    private int myCount;
    private int myn;

    public ModNCounter(int n) {
        myn=n;
        myCount = 0;
    }

    public void increment() {
        myCount++;
    }

    public void reset() {
        myCount = 0;
    }

    public int value() {
        return myCount%myn;
    }

}
