package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;
import org.benedetto.ifr.feedback.consumers.AwgFeedBackConsumer;
import org.benedetto.ifr.feedback.generators.ItemBiasedFeedBackGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.benedetto.ifr.TestUtil.gsonPrint;

public class RandomFeedbackTest {

    /**
     * This test generates clicks and adds them to an acyclic weighted graph.
     * Subsequently the transitive closure of this graph is generated, and an example page
     * is sorted using a node comparator based on the transitive closure.
     */

    @Test
    public void testTopologicalSort() {
        ItemBiasedFeedBackGenerator feedBackGenerator = new ItemBiasedFeedBackGenerator(10, 50, 100, 1.0f);
        gsonPrint(feedBackGenerator.attractivity);
        AwgFeedBackConsumer<Integer> consumer = new AwgFeedBackConsumer<>();
        while (feedBackGenerator.hasNext()) {
            FeedBack<Integer> f = feedBackGenerator.next();
            consumer.addFeedBack(f);
        }
        ClosureGraph<Integer> cg = new ClosureGraph<>(consumer.acyclicWeightedGraph);
        List<Integer> page = Arrays.asList(4,6,8);
        cg.sort(page);
        gsonPrint(page);
    }

    @Test
    public void simpleTest() throws InvalidFeedBackException {
        AwgFeedBackConsumer<Integer> consumer = new AwgFeedBackConsumer<>();
        FeedBack<Integer> feedBack = new FeedBack<>(3, Arrays.asList(1,2,3,4,5));
        consumer.addFeedBack(feedBack);
        gsonPrint(consumer);
    }
}
