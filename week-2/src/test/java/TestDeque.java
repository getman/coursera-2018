import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ArtemParfenov on 30.06.2018.
 */
public class TestDeque {
    @Test
    public void mustBeEmptyAfterInitialization() {
        Deque<String> d = new Deque<String>();
        Assert.assertEquals("emptyAfterInit", Boolean.TRUE, d.isEmpty());
        System.out.println("emptyAfterInit - OK");
    }

    @Test
    public void mustNotBeEmptyAfterAddingOneElement() {
        Deque<String> d = new Deque<String>();
        d.addFirst("fffuuu");
        Assert.assertEquals("notEmptyAfterAddingOneElement", Boolean.FALSE, d.isEmpty());
        System.out.println("notEmptyAfterAddingOneElement - OK");
    }

    @Test
    public void mustBeEmptyAfterAddingAndRemovingOneElement() {
        Deque<String> d = new Deque<String>();
        d.addFirst("fffuuu");
        d.removeFirst();
        Assert.assertEquals("emptyAfterAddingAndRemovingOneElement", Boolean.TRUE, d.isEmpty());
        System.out.println("emptyAfterAddingAndRemovingOneElement - OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForNullAddFirst() {
        Deque<String> d = new Deque<String>();
        d.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForNullAddLast() {
        Deque<String> d = new Deque<String>();
        d.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionForRemoveFirstOnEmptyDeque() {
        Deque<String> d = new Deque<String>();
        d.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionForRemoveLastOnEmptyDeque() {
        Deque<String> d = new Deque<String>();
        d.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionForNextWhenNoMoreElements() {
        Deque<String> d = new Deque<String>();
        Iterator iterator = d.iterator();
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationExceptionForRemove() {
        Deque<String> d = new Deque<String>();
        Iterator iterator = d.iterator();
        iterator.remove();
    }
}
