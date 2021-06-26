public class Measurement {

    /**
     * Constructor: initialize this object to be a measurement of 0 feet, 0
     * inches
     */
    private int inchesnumber;
    private int feetnumber;

    public Measurement() {
        inchesnumber = 0;
        feetnumber = 0;
    }

    /**
     * Constructor: takes a number of feet as its single argument, using 0 as
     * the number of inches
     */
    public Measurement(int feet) {
        feetnumber = feet;
        inchesnumber = 0;
    }

    /**
     * Constructor: takes the number of feet in the measurement and the number
     * of inches as arguments (in that order), and does the appropriate
     * initialization
     */
    public Measurement(int feet, int inches) {
        feetnumber = feet;
        inchesnumber = inches;
    }

    /**
     * Returns the number of feet in in this Measurement. For example, if the
     * Measurement has 1 foot and 6 inches, this method should return 1.
     */
    public int getFeet() {
        return feetnumber; // provided to allow the file to compile
    }

    /**
     * Returns the number of inches in this Measurement. For example, if the
     * Measurement has 1 foot and 6 inches, this method should return 6.
     */
    public int getInches() {
        return inchesnumber; // provided to allow the file to compile
    }

    /**
     * Adds the argument m2 to the current measurement
     */
    public Measurement plus(Measurement m2) {
        int newfeet = m2.getFeet() + feetnumber;
        int newinches = m2.getInches() + inchesnumber;// provided to allow the file to compile
        return new Measurement(newfeet, newinches);
    }

    /**
     * Subtracts the argument m2 from the current measurement. You may assume
     * that m2 will always be smaller than the current measurement.
     */
    public Measurement minus(Measurement m2) {
        int newfeet = feetnumber - m2.getFeet();
        int newinches = inchesnumber - m2.getInches();
        return new Measurement(newfeet, newinches); // provided to allow the file to compile
    }

    /**
     * Takes a nonnegative integer argument n, and returns a new object that
     * represents the result of multiplying this object's measurement by n. For
     * example, if this object represents a measurement of 7 inches, multiple
     * (3) should return an object that represents 1 foot, 9 inches.
     */
    public Measurement multiple(int multipleAmount) {
        int temp = feetnumber*12 + inchesnumber;
        temp=temp*multipleAmount;
        int newFeet=temp/12;
        int newInches=temp%12;
        return new Measurement(newFeet,newInches); // provided to allow the file to compile
    }

    /**
     * toString should return the String representation of this object in the
     * form f'i" that is, a number of feet followed by a single quote followed
     * by a number of inches less than 12 followed by a double quote (with no
     * blanks).
     */
    @Override
    public String toString() {
        String result;
        result=feetnumber+"'"+inchesnumber+ "\"";
        return result; // provided to allow the file to compile
    }

}