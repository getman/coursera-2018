import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ArtemParfenov on 01.07.2018.
 */
public class TestRandomizedQueue {
    @Test
    public void mustBeInitializedEmpty () {
        RandomizedQueue rq = new RandomizedQueue();
        Assert.assertEquals(rq.isEmpty(), Boolean.TRUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mustThrowIllegalArgumentExceptionIfEnqueWithNull() {
        RandomizedQueue rq = new RandomizedQueue();
        rq.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void mustThrowNoSuchElementExceptionWhenCallSampleOnEmptyQueue() {
        RandomizedQueue rq = new RandomizedQueue();
        rq.sample();
    }

    @Test(expected = NoSuchElementException.class)
    public void mustThrowNoSuchElementExceptionWhenCallDequeueOnEmptyQueue() {
        RandomizedQueue rq = new RandomizedQueue();
        rq.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void mustThrowNoSuchElementExceptionWhenCallNextAndNoElements() {
        RandomizedQueue rq = new RandomizedQueue();
        Iterator it = rq.iterator();
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void mustThrowUnsupportedOperationExceptionWhenCallRemove() {
        RandomizedQueue rq = new RandomizedQueue();
        Iterator it = rq.iterator();
        it.remove();
    }
}
