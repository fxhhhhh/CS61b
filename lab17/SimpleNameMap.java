import java.util.LinkedList;

public class SimpleNameMap<E> {

    /* TODO: Instance variables here */
    LinkedList[] initialArray = new LinkedList[10];
    double loadFactor = 0.75;


    public SimpleNameMap() {
        // TODO: YOUR CODE HERE
        for (int i = 0; i < initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            initialArray[i] = initialList;
        }
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        // TODO: YOUR CODE HERE
        int count = 0;
        for (int i = 0; i < initialArray.length; i++) {
            if (initialArray[i].size() != 0) {
                count += initialArray[i].size();
            }
        }
        return count;
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        // TODO: YOUR CODE HERE
        if (size()!=0&&initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size() != 0) {
            Entry temp = new Entry(key, null);
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                if (temp.keyEquals((Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        // TODO: YOUR CODE HERE
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                Entry temp = (Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i);
                if (temp.keyEquals(new Entry(key, null))) {
                    return temp.value;
                }
            }
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        // TODO: YOUR CODE HERE
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                Entry temp = (Entry) initialArray[key.hashCode()].get(i);
                if (temp.keyEquals(new Entry(key, value))) {
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].remove(i);
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].add(i, new Entry(key, value));
                }
            }
        } else {
            double nowLoadFactor=(size()+ 1) / (double) initialArray.length;
            if (size()==0|| nowLoadFactor < loadFactor) {
                initialArray[Math.floorMod(key.hashCode(), initialArray.length)].add(new Entry(key, value));
            } else {
                resize(key, value);
            }
        }

    }
    public void modifySize(int n){
        initialArray=new LinkedList[n];
    }

    public void resize(String key, String value) {
        SimpleNameMap temp =new SimpleNameMap();
        temp.modifySize(2*initialArray.length);
        for (int i = 0; i < temp.initialArray.length; i++) {
            LinkedList<String> initialList = new LinkedList<>();
            temp.initialArray[i] = initialList;
        }
        for(int i =0;i<initialArray.length;i++){
            for (int j =0;j<initialArray[i].size();j++){
                Entry element = (Entry) initialArray[i].get(j);
                temp.put(element.key,element.value);
            }
        }
        temp.put(key,value);
        this.initialArray=temp.initialArray;

    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        // TODO: YOUR CODE HERE
        if (containsKey(key)) {
            for (int i = 0; i < initialArray[Math.floorMod(key.hashCode(), initialArray.length)].size(); i++) {
                Entry temp = (Entry) initialArray[Math.floorMod(key.hashCode(), initialArray.length)].get(i);
                if (temp.keyEquals(new Entry(key, null))) {
                    initialArray[Math.floorMod(key.hashCode(), initialArray.length)].remove(i);
                    return temp.value;
                }
            }

        }
        return null;
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}