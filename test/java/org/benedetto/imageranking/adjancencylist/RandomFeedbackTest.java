package org.benedetto.imageranking.adjancencylist;

import org.benedetto.imageranking.adjancencylist.FeedBack;
import org.benedetto.imageranking.adjancencylist.FeedBackGenerator;
import org.benedetto.imageranking.adjancencylist.NodeComparator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.benedetto.imageranking.TestUtil.gsonPrint;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 11/2/13
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class RandomFeedbackTest {

    @Test
    public void test() {
        FeedBackGenerator feedBackGenerator = new FeedBackGenerator(5, 20, 10);
        gsonPrint(feedBackGenerator.attractivity);
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        while (feedBackGenerator.hasNext()) {
            FeedBack f = feedBackGenerator.next();
            gsonPrint(f);
            g.addFeedBack(f);
        }
        ClosureGraph cg = new ClosureGraph(g);
        NodeComparator nc = new NodeComparator(cg);
        List<Integer> page = Arrays.asList(new Integer[] {4,6,8});
        Collections.sort(page, nc);
        gsonPrint(page);
    }
}
