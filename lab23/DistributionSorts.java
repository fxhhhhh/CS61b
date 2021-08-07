import java.util.Arrays;

public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        // TODO: YOUR CODE HERE
        int[] count = new int[10];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]] += 1;
        }
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += count[i];
            for (int j = sum - count[i]; j < sum; j++) {
                arr[j] = i;
            }
        }
    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {
        // TODO: YOUR CODE HERE
        int[] count = new int[10];
        int number=0;
        for (int i = 0; i < arr.length; i++) {
            number=arr[i];
            for(int j=0;j<digit;j++){
                number=number/10;
            }
            count[number%10] += 1;
        }
        int[] result = new int[arr.length];
        int j = 0;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < arr.length; k++) {
                number=arr[k];
                for(int m=0;m<digit;m++) {
                    number = number / 10;
                }
                if (number % 10 == i) {
                    result[j] = arr[k];
                    j += 1;
                }
            }
        }
        System.arraycopy(result, 0, arr, 0, arr.length);
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /* Returns a random integer between 0 and 9999. */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /* Returns a random integer between 0 and 9. */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    private static void runCountingSort(int len) {
        int[] arr1 = new int[len];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }
    }

    private static void runLSDRadixSort(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

    public static void main(String[] args) {
        runCountingSort(20);
        runLSDRadixSort(3);
//        int[] arr = new int[]{356, 112, 904, 294, 209, 820, 394, 810};
//        lsdRadixSort(arr);
//        System.out.println("Should be sorted: " + Arrays.toString(arr));
        runLSDRadixSort(30);
    }
}