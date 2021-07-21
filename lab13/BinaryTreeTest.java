import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BinaryTreeTest {
    @Test
    public void treeFormatTest() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();
        x.add("C");
        x.add("A");
        x.add("E");
        x.add("B");
        x.add("D");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertEquals("x in preorder\nC A B E D \nx in inorder\nA B C D E \n\n".trim(),
                     outContent.toString().trim());
    }
    @Test
    public void treeFormatTestInt() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();
        x.add("2");
        x.add("1");
        x.add("3");
        x.add("4");
        x.add("0");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertEquals("x in preorder\n2 1 0 3 4 \nx in inorder\n0 1 2 3 4 \n\n".trim(),
                outContent.toString().trim());
    }
    @Test
    public void treeRoot() {
        BinarySearchTree<Integer> x = new BinarySearchTree<Integer>();
        x.add(2);
        x.add(1);
        x.add(3);
        x.add(4);
        x.add(0);
        x.add(0);
        assertTrue(x.contains(0));
    }
}