import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ArtemParfenov on 30.06.2018.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /** quick-union weighted with path comression id array*/
    Node<Item> [] idArray;
    int nodeAmount;
    //points to the first empty node index
    int firstEmptyNodeId = 0;
    // construct an empty randomized queue
    public RandomizedQueue() {
        idArray = new Node[1];
    }
    // is the randomized queue empty?
    public boolean isEmpty() {
        return nodeAmount == 0;
    }
    // return the number of items on the randomized queue
    public int size() {
        return nodeAmount;
    }
    // add the item
    public void enqueue(Item newItem) {
        Node newNode = new Node(newItem);
        idArray[firstEmptyNodeId] = newNode;
        //iterating through the array until we will find an empty cell, the cell next to last be our last chance
        while((idArray[firstEmptyNodeId] != null) && (firstEmptyNodeId < idArray.length)){
            firstEmptyNodeId++;
        }
        nodeAmount++;
        resizeIdArray();
    }
    // remove and return a random item
    public Item dequeue() {
        int randomItem = edu.
        firstEmptyNodeId = 0;
        nodeAmount--;
        return null;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        return null;
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return null;
    }

    /**resizes id array by 2x bigger if the nodes amount is more than id array size
     * or by 1/4 smaller if the nodes amount less than id array size/2.
     * after that copies id array data*/
    private void resizeIdArray() {
        Node<Item>[] newIdArray = null;
        if (nodeAmount > idArray.length - 1) {
            newIdArray = new Node[idArray.length*2];
            copyArray(idArray, newIdArray);
        } else if (nodeAmount < idArray.length/2) {
            newIdArray = new Node[idArray.length - idArray.length/4];
            copyArray(idArray, newIdArray);
        }
    }

    private void copyArray(Node[] src, Node[] dest) {
        int destInd = 0;
        for (Node nextNode: src) {
            if (nextNode != null) {
                dest[destInd++] = nextNode;
            }
        }
    }

    /**Represents an element of the deque*/
    private static class Node<Item> {
        private Item value;
        private Node nextNode;
        public Node() {}
        public Node(Item newItem) {
            this.value = newItem;
        }
        public Item getValue() {
            return value;
        }

        public void setValue(Item value) {
            this.value = value;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

    }

    private static class NodeIterator<Item> implements Iterator {
        Node currentNode;
        /**Parameterized constructor with the first node*/
        public NodeIterator(Node firstNode) {
            this.currentNode = firstNode;
        }
        public boolean hasNext() {
            return currentNode.getNextNode() != null;
        }

        public Object next() {
            if (currentNode.getNextNode() == null) {
                throw new NoSuchElementException();
            }
            return currentNode.getNextNode();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}
