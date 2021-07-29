import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {
    /* TODO: Instance variables here */
    LinkedList[] initialArray;
    double initialLoadFactor;

    HashMap() {
        initialArray = new LinkedList[16];
        for (int i = 0; i < initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            initialArray[i] = initialList;
        }
        initialLoadFactor = 0.75;
    }

    /* Creates a new hash map with an array of size INITIALCAPACITY and a maximum load factor of 0.75. */
    HashMap(int initialCapacity) {
        initialArray = new LinkedList[initialCapacity];
        for (int i = 0; i < initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            initialArray[i] = initialList;
        }
        initialLoadFactor = 0.75;
    }

    /* Creates a new hash map with INITIALCAPACITY and LOADFACTOR. */
    HashMap(int initialCapacity, double loadFactor) {
        initialArray = new LinkedList[initialCapacity];
        for (int i = 0; i < initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            initialArray[i] = initialList;
        }
        initialLoadFactor = loadFactor;

    }

    @Override
    public void clear() {
        initialArray = new LinkedList[initialArray.length];
        for (int i = 0; i < initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            initialArray[i] = initialList;
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (size() != 0 && initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size() != 0) {
            HashMap.Entry temp = new HashMap.Entry(key, null);
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                if (temp.keyEquals((HashMap.Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                HashMap.Entry temp = (HashMap.Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i);
                if (temp.keyEquals(new HashMap.Entry(key, null))) {
                    return (V) temp.value;
                }
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                HashMap.Entry temp = (HashMap.Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i);
                if (temp.keyEquals(new HashMap.Entry(key, value))) {
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].remove(i);
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].add(i, new HashMap.Entry(key, value));
                }
            }
        } else {
            double nowLoadFactor = (size() + 1) / (double) initialArray.length;
            if (size() == 0 || nowLoadFactor <= initialLoadFactor) {
                initialArray[Math.floorMod(key.hashCode(), initialArray.length)].add(new HashMap.Entry(key, value));
            } else {
                resize(key, value);
            }
        }

    }

    public void modifySize(int n) {
        initialArray = new LinkedList[n];
    }

    public void resize(K key, V value) {
        HashMap temp = new HashMap();
        temp.modifySize(2 * initialArray.length);
        for (int i = 0; i < temp.initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            temp.initialArray[i] = initialList;
        }
        for (int i = 0; i < initialArray.length; i++) {
            for (int j = 0; j < initialArray[i].size(); j++) {
                HashMap.Entry element = (HashMap.Entry) initialArray[i].get(j);
                temp.put(element.key, element.value);
            }
        }
        temp.put(key, value);
        this.initialArray = temp.initialArray;

    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                HashMap.Entry temp = (HashMap.Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i);
                if (temp.keyEquals(new HashMap.Entry(key, null))) {
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].remove(i);
                    return (V) temp.value;
                }
            }

        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                HashMap.Entry temp = (HashMap.Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i);
                if (temp.equals(new HashMap.Entry(key, value))) {
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].remove(i);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public int size() {
        int count = 0;
        for (int i = 0; i < initialArray.length; i++) {
            if (initialArray[i].size() != 0) {
                count += initialArray[i].size();
            }
        }
        return count;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }


    /* Returns the length of this HashMap's internal array. */
    public int capacity() {
        return initialArray.length;
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(HashMap.Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof HashMap.Entry
                    && key.equals(((HashMap.Entry) other).key)
                    && value.equals(((HashMap.Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
