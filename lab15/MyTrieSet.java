import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61BL {
    private static final int R = 256;        // extended ASCII

    private Node root = new Node(' ', false);      // root of trie

    private static class Node {
        private char val;
        private boolean isKey;
        private Map map = new HashMap();

        public Node(char c, boolean b) {
            val = c;
            isKey = b;
        }
    }

    @Override
    public void clear() {
        root = new Node(' ', false);
    }

    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (curr != null)
                if (!curr.map.containsKey(c)) {
                    return false;
                }
            curr = (Node) curr.map.get(c);
        }
        return true;
    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = (Node) curr.map.get(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> allWords = new ArrayList();
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            curr = (Node) curr.map.get(c);
        }
        for (Object element : curr.map.keySet()) {
            String a;
            a= Character.toString((Character) element);
            keysWithPrefixHelper(a, allWords, (Node) curr.map.get(element));
        }
        List<String> result = new ArrayList();
        for (Object element : allWords) {
            element = prefix + element;
            result.add((String) element);
        }
        return result;
    }

    public void keysWithPrefixHelper(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }
        for (Object element : n.map.keySet()) {
            keysWithPrefixHelper(s + element, x, (Node) n.map.get(element));
        }

    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();

    }

}
