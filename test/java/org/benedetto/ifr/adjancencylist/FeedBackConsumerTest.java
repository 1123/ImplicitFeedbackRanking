package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;
import org.benedetto.ifr.feedback.consumers.AwgFeedBackConsumer;
import org.benedetto.ifr.util.ComparablePair;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

public class FeedBackConsumerTest {

    @Test
    public void testStatistics() throws InvalidFeedBackException {
        FeedBack<Integer> first = new FeedBack<>(3, Arrays.asList(1,2,3));
        AwgFeedBackConsumer<Integer> consumer = new AwgFeedBackConsumer<>();
        consumer.addFeedBack(first);
        FeedBack<Integer> second = new FeedBack<>(2, Arrays.asList(4,3,2));
        consumer.addFeedBack(second);
        FeedBack<Integer> third = new FeedBack<>(3, Arrays.asList(4,3));
        consumer.addFeedBack(third);
        assertTrue(consumer.acyclicWeightedGraph.nodes().size() == 4);
        assertTrue(consumer.statistics.size() == 2);
        assertTrue(consumer.statistics.get(3).get(3) == 2);
        assertTrue(consumer.statistics.get(2).get(2) == 1);
    }

    @Test(expected=InvalidFeedBackException.class)
    public void testInvalidFeedBack() throws InvalidFeedBackException {
        AwgFeedBackConsumer<Integer> consumer = new AwgFeedBackConsumer<>();
        consumer.addFeedBack(new FeedBack<>(3, Arrays.asList(1,2)));
    }

    @Test
    public void testComparablePair() {
        ComparablePair<Integer, Integer> first = new ComparablePair<>(3,3);
        ComparablePair<Integer, Integer> second = new ComparablePair<>(3,3);
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
        assertTrue(first.hashCode() == second.hashCode());
    }

}
