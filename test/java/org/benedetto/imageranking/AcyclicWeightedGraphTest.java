package org.benedetto.imageranking;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.benedetto.imageranking.FloatUtils.floatEqual;
import static org.benedetto.imageranking.TestUtil.gsonPrint;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/31/13
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class AcyclicWeightedGraphTest {

    @Test
    public void simpleTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.2f);
        g.addEdge(2, 3, 0.3f);
        gsonPrint(g);
    }

    @Test
    public void doubleEdgeTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(1, 2, 0.2f);
        gsonPrint(g);
        assertTrue(floatEqual(g.get(1).get(2), 0.3f));
    }

    @Test
    public void simpleCycleTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        gsonPrint(g);
        List<Integer> cycle = g.search(1,3);
        gsonPrint(cycle);
        assertEquals(cycle, Arrays.asList(new Integer[]{1, 2, 3}));
    }

    @Test
    public void simpleCylceRemovalTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 1, 0.2f);
        assertTrue(floatEqual(g.get(2).get(1), 0.1f));
        assertFalse(g.get(1).containsKey(2));
    }

    public void longCycleRemovalTest() {
        AcyclicWeightedGraph graph = new AcyclicWeightedGraph();
    }

}
