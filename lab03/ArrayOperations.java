public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        // TODO: YOUR CODE HERE
        for (int i = 0; i < values.length; i += 1) {
            if (i == values.length - 1) {
                break;
            } else {
                if (i >= pos) {
                    values[i] = values[i + 1];
                }
            }
        }
        values[values.length - 1] = 0;
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        // TODO: YOUR CODE HERE
        for (int i = values.length - 1; i > pos; i -= 1) {
            values[i] = values[i - 1];
        }
        values[pos] = newInt;
    }

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    public static int[] catenate(int[] A, int[] B) {
        // TODO: YOUR CODE HERE
        if (A == null) {
            return B;
        }
        if (B == null) {
            return A;
        }
        int[] result = new int[A.length + B.length];
        System.arraycopy(A, 0, result, 0, A.length);
        System.arraycopy(B, 0, result, A.length, B.length);
        return result;
    }

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     */
    public static int[][] naturalRuns(int[] A) {
        // TODO: Your CODE HERE
        int times = 1;
        int pre = A[0];
        for (int i = 1; i < A.length; i += 1) {
            if (A[i] < pre) {
                times += 1;
            }
            pre = A[i];
        }
        int[][] total = new int[times][];
        int[] result = new int[1];
        int j = 0;
        result[0]= A[0];
        for (int i = 0; i < A.length - 1; i++) {
            if (A[i] < A[i + 1]) {
                result = catenate(result, new int[]{A[i + 1]});
            } else {
                total[j] = result;
                result = new int[1];
                result[0] = A[i + 1];
                j++;
            }
        }
        total[j]=result;

        return total;
    }

    /*
     * Returns the subarray of A consisting of the LEN items starting
     * at index K.
     */
    public static int[] subarray(int[] A, int k, int len) {
        int[] result = new int[len];
        System.arraycopy(A, k, result, 0, len);
        return result;
    }

}