import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        // TODO
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            list.add(values[i]);
        }
        for (int i = 0; i < values.length; i++) {
            if (!list.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */
    public static boolean sumTo(int[] values, int n) {
        // TODO
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < values.length; i++) {
            set.add(values[i]);
        }
        int times = 0;
        for (int i = 0; i < n / 2; i++) {
            if (set.contains(n - i) && set.contains(i) && n - i != i) {
                times += 1;
            }
        }
        return times == 1;

    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        // TODO
        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            int times = 0;
            for (int j = 0; j < s1.length(); j++) {
                if (s1.charAt(j) == s1.charAt(i)) {
                    times += 1;
                }
                map1.put(s1.charAt(i), times);
            }
        }
        for (int i = 0; i < s2.length(); i++) {
            int times = 0;
            for (int j = 0; j < s2.length(); j++) {
                if (s2.charAt(j) == s2.charAt(i)) {
                    times += 1;
                }
                map2.put(s2.charAt(i), times);
            }
        }
        for (char a:map1.keySet()){
            if(map1.get(a)!= map2.get(a)){
                return false;
            }
        }
        return true;

    }

}