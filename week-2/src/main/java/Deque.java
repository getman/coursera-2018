import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ArtemParfenov on 30.06.2018.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    //reference to the first element
    Node first;
    //reference to the tail element
    Node last;
    // construct an empty deque
    public Deque() {
        first = new Node();
        last = first;
        first.setNextNode(null);
        last.setNextNode(null);
    }
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // return the number of items on the deque
    public int size() {
        return size;
    }
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newFirstNode = new Node(item);
        Node oldFirsNode = this.first;
        newFirstNode.setNextNode(oldFirsNode);
        this.first = newFirstNode;
        size++;
    }
    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newLastNode = new Node(item);
        Node oldLastNode = this.last;
        oldLastNode.setNextNode(newLastNode);
        newLastNode.setNextNode(null);
        this.last = newLastNode;
        size++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        size--;
        return null;
    }
    // remove and return the item from the end
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        size--;
        return null;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        Iterator i = new NodeIterator<Item>(this.first);
        return i;
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
