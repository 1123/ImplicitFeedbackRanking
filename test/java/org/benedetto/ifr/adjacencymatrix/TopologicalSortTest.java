package org.benedetto.ifr.adjacencymatrix;

import com.google.common.collect.Lists;
import org.benedetto.ifr.adjancencylist.AcyclicWeightedGraph;
import org.benedetto.ifr.adjancencylist.ClosureGraph;
import org.benedetto.ifr.adjancencylist.FeedBack;
import org.benedetto.ifr.adjancencylist.NodeComparator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 12/26/13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
public class TopologicalSortTest {

    @Test
    public void test() {
        AcyclicWeightedGraph<String> graph = new AcyclicWeightedGraph<>();
        FeedBack first = new FeedBack("c", Arrays.asList(new String [] { "a", "b", "c" }));
        graph.addFeedBack(first);
        FeedBack second = new FeedBack("b", Arrays.asList(new String [] { "e", "b", "d" }));
        graph.addFeedBack(second);
        NodeComparator<String> nodeComparator =
                new NodeComparator(new ClosureGraph<String>(graph));
        ArrayList<String> nodes = new ArrayList<>(graph.nodes());
        Collections.sort(nodes, nodeComparator);
        List<String> nodesReverted = Lists.reverse(nodes);
        System.err.println(nodesReverted);
        assertTrue(nodesReverted.indexOf("a") > nodesReverted.indexOf("c"));
        assertTrue(nodesReverted.indexOf("b") > nodesReverted.indexOf("c"));
        assertTrue(nodesReverted.indexOf("e") > nodesReverted.indexOf("b"));
        assertTrue(nodesReverted.indexOf("d") > nodesReverted.indexOf("b"));
    }
}
