import java.util.Iterator;
import java.util.LinkedList;

public class BST<T> {

    BSTNode<T> root;

    public BST(LinkedList<T> list) {
        root = sortedIterToTree(list.iterator(), list.size());
    }

    /* Returns the root node of a BST (Binary Search Tree) built from the given
       iterator ITER  of N items. ITER will output the items in sorted order,
       and ITER will contain objects that will be the item of each BSTNode. */
    private BSTNode<T> sortedIterToTree(Iterator<T> iter, int N) {
        // TODO: YOUR CODE HERE
        root = sortedIterToTreeHelper(iter, N);
        return root;
    }

    private BSTNode<T> sortedIterToTreeHelper(Iterator<T> iter, int N) {
        if (N == 0) {
            return null;
        }
        if (N == 1) {
            return new BSTNode<>(iter.next());
        }
        if (N == 2) {
            BSTNode temp = new BSTNode<>(iter.next());
            temp.right = new BSTNode<>(iter.next());
            return temp;
        } else {
            BSTNode left = sortedIterToTree(iter, N / 2);
            BSTNode temp = new BSTNode<>(iter.next());
            temp.left=left;
            temp.right = sortedIterToTree(iter, N / 2-1 );
            return temp;
        }
    }

    /* Prints the tree represented by ROOT. */
    private void print() {
        print(root, 0);
    }

    private void print(BSTNode<T> node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    class BSTNode<T> {
        T item;
        BSTNode<T> left;
        BSTNode<T> right;

        BSTNode(T item) {
            this.item = item;
        }
    }

}
