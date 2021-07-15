import java.util.HashSet;
import java.util.Set;

public class UnionFind {

    /* TODO: Add instance variables here. */
    private int[] unionArray;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        unionArray = new int[N];
        for (int i = 0; i < N; i++) {
            unionArray[i] = -1;
        }

    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if (unionArray[v] >=0) {
            return sizeOf(unionArray[v]);
        } else {
            return -unionArray[v];
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if (unionArray[v] > 0) {
            return unionArray[v];
        }
        return sizeOf(v);
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (parent(v1) == parent(v2) && unionArray[v1] != -1) {
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        Set<Integer> a = new HashSet<>();
        while (unionArray[v] >= 0) {
            a.add(unionArray[v]);
            v = unionArray[v];
        }
        for (Integer i : a) {
            unionArray[i] = v;
        }
        return v;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        if (unionArray[root1] < unionArray[root2]) {
            unionArray[root1] = unionArray[root1] + unionArray[root2];
            unionArray[root2]=root1;
        }else {
            unionArray[root2] = unionArray[root1] + unionArray[root2];
            unionArray[root1]=root2;
        }
    }
    
}
