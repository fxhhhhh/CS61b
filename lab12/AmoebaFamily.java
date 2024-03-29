import edu.princeton.cs.algs4.Stack;

import java.util.*;

/* An AmoebaFamily is a tree, where nodes are Amoebas, each of which can have
   any number of children. */
public class AmoebaFamily implements Iterable<AmoebaFamily.Amoeba> {

    /* ROOT is the root amoeba of this AmoebaFamily */
    private Amoeba root = null;

    /* Creates an AmoebaFamily, where the first Amoeba's name is NAME. */
    public AmoebaFamily(String name) {
        root = new Amoeba(name, null);
    }

    /* Adds a new Amoeba with CHILDNAME to this AmoebaFamily as the youngest
       child of the Amoeba named PARENTNAME. This AmoebaFamily must contain an
       Amoeba named PARENTNAME. */
    public void addChild(String parentName, String childName) {
        if (root != null) {
            root.addChildHelper(parentName, childName);
        }
    }

    /* Prints the name of all Amoebas in this AmoebaFamily in preorder, with
       the ROOT Amoeba printed first. Each Amoeba should be indented four spaces
       more than its parent. */
    public void print() {
        // TODO: YOUR CODE HERE
        if (root == null) {
            return;
        }
        root.printAmoeba(0);
    }

    /* Returns the length of the longest name in this AmoebaFamily. */
    public int longestNameLength() {
        if (root != null) {
            return root.longestNameLengthHelper();
        }
        return 0;
    }

    /* Returns the longest name in this AmoebaFamily. */
    public String longestName() {
        // TODO: YOUR CODE HERE
        if (root == null) {
            return null;
        } else {
            return root.longestNameHelper();
        }
    }

    /* Returns an Iterator for this AmoebaFamily. */
    public Iterator<Amoeba> iterator() {
        return new AmoebaBFSIterator();
    }

    /* Creates a new AmoebaFamily and prints it out. */
    public static void main(String[] args) {
        AmoebaFamily family = new AmoebaFamily("[");
        family.addChild("[", "(");
        family.addChild("[", ".");
        family.addChild("[", "_");
        family.addChild("(", ".");
        family.print();
        Iterator<Amoeba> seer = family.iterator();
        while (seer.hasNext()) {
            String x = seer.next().name;
            System.out.println(x);
        }
    }

    /* An Amoeba is a node of an AmoebaFamily. */
    public static class Amoeba {

        private String name;
        private Amoeba parent;
        private ArrayList<Amoeba> children;

        public Amoeba(String name, Amoeba parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<Amoeba>();
        }

        public String toString() {
            return name;
        }

        public Amoeba getParent() {
            return parent;
        }

        public ArrayList<Amoeba> getChildren() {
            return children;
        }

        /* Adds child with name CHILDNAME to an Amoeba with name PARENTNAME. */
        public void addChildHelper(String parentName, String childName) {
            if (name.equals(parentName)) {
                Amoeba child = new Amoeba(childName, this);
                children.add(child);
            } else {
                for (Amoeba a : children) {
                    a.addChildHelper(parentName, childName);
                }
            }
        }

        /* Returns the length of the longest name between this Amoeba and its
           children. */
        public int longestNameLengthHelper() {
            int maxLengthSeen = name.length();
            for (Amoeba a : children) {
                maxLengthSeen = Math.max(maxLengthSeen,
                        a.longestNameLengthHelper());
            }
            return maxLengthSeen;
        }

        // TODO: ADD HELPER FUNCTIONS HERE
        public void printAmoeba(int k) {
            Amoeba parent = this;
            if (parent.getChildren() == null) {
                return;
            } else {
                String b = "";
                for (int i = 0; i < k; i++) {
                    b += "    ";
                }
                System.out.println(b + parent);
                for (Amoeba a : parent.children) {
                    a.printAmoeba(k + 1);
                }
            }
        }

        public String longestNameHelper() {
            String maxLengthSeen = name;
            for (Amoeba a : children) {
                if (a.longestNameHelper() != null) {
                    if (maxLengthSeen.length() < a.longestNameHelper().length()) {
                        maxLengthSeen = a.longestNameHelper();
                    }
                }
            }
            return maxLengthSeen;
        }
    }

    /* An Iterator class for the AmoebaFamily, running a DFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaDFSIterator implements Iterator<Amoeba> {

        // TODO: IMPLEMENT THE CLASS HERE
        private Stack<Amoeba> fringe = new Stack<Amoeba>();

        /* AmoebaDFSIterator constructor. Sets up all of the initial information
           for the AmoebaDFSIterator. */
        public AmoebaDFSIterator() {
            if (root != null) {
                fringe.push(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                return null;
            }
            Amoeba node = fringe.pop();
            for (int i=0; i< node.children.size();i++) {
                fringe.push(node.children.get(node.children.size()-1-i));
            }
            return node;
        }

        public void remove() {
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }
            fringe.pop();
        }
    }

    /* An Iterator class for the AmoebaFamily, running a BFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaBFSIterator implements Iterator<Amoeba> {

        // TODO: IMPLEMENT THE CLASS HERE
        private Queue<Amoeba> fringe = new LinkedList<Amoeba>();

        /* AmoebaBFSIterator constructor. Sets up all of the initial information
           for the AmoebaBFSIterator. */
        public AmoebaBFSIterator() {
            if (root != null) {
                fringe.add(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                return null;
            }
            Amoeba node = fringe.remove();
            for (Amoeba i : node.children) {
                fringe.add(i);
            }
            return node;
        }
        public void remove() {
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }
            fringe.remove();
        }
    }

}
