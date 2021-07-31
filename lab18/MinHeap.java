import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not 
    // implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        // TODO: YOUR CODE HERE
        if (2 * index >= contents.size()) {
            return 0;
        } else {
            return 2 * index;
        }
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        // TODO: YOUR CODE HERE
        if (2 * index +1 >= contents.size()) {
            return 0;
        } else {
            return 2 * index + 1;
        }
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        // TODO: YOUR CODE HERE
        if (index >= contents.size()) {
            return 0;
        } else {
            return index / 2;
        }
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        // TODO: YOUR CODE HERE
        if (contents.get(index1).compareTo(contents.get(index2)) > 0) {
            return index2;
        }
        return index1;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        // TODO: YOUR CODE HERE
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        // TODO: YOUR CODE HERE
        while (index != 1 && contents.get(index).compareTo(contents.get(getParentOf(index))) < 0) {
            swap(index, getParentOf(index));
            index = getParentOf(index);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        // TODO: YOUR CODE HERE
        while (getLeftOf(index) != 0 && contents.get(index).compareTo(contents.get(getLeftOf(index))) > 0) {
            swap(index, getLeftOf(index));
            index = getLeftOf(index);
        }
        while (getRightOf(index) != 0 && contents.get(index).compareTo(contents.get(getRightOf(index))) > 0) {
            swap(index, getRightOf(index));
            index = getRightOf(index);
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        // TODO: YOUR CODE HERE
        return contents.size();
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        // TODO: YOUR CODE HERE
        if (contents.contains(element)) {
            throw new IllegalArgumentException();
        }
        setElement(size(), element);
        bubbleUp(size() - 1);
        size += 1;
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        // TODO: YOUR CODE HERE
        E min = contents.get(1);
        swap(1, contents.size() - 1);
        contents.remove(contents.size() - 1);
        bubbleDown(1);
        size -= 1;
        return min;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        // TODO: YOUR CODE HERE
        if (contains(element)) {
            int index = 0;
            for (int i = 1; i < size(); i++) {
                if (element.equals(contents.get(i))) {
                    index = i;
                    break;
                }
            }
            setElement(index, element);
            if (getParentOf(index)!=0&&element.compareTo(getElement(getParentOf(index))) < 0) {
                bubbleUp(index);
            }
            if (getLeftOf(index) != 0&&element.compareTo(getElement(getLeftOf(index))) > 0) {
                bubbleDown(index);
            }
            if (getRightOf(index) != 0&&element.compareTo(getElement(getRightOf(index))) > 0) {
                bubbleDown(index);
            }
        }else {
        throw new NoSuchElementException();
    }}

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        // TODO: YOUR CODE HERE
        if(contents.contains(element)){
            return true;
        }
        for (int i = 1; i < size; i ++) {
            if(element.equals(contents.get(i)))
            {
                return true;
            }
        }
        return false;
    }
}