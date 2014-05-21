package org.benedetto.ifr.adjancencylist;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;
import static org.benedetto.ifr.util.FloatUtils.floatEqual;
import static org.benedetto.ifr.TestUtil.gsonPrint;

public class AcyclicWeightedGraphTest {

    @Test
    public void simpleTest() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(1, 2, 0.2f);
        g.addEdge(2, 3, 0.3f);
        gsonPrint(g);
    }

    @Test
    public void doubleEdgeTest() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(1, 2, 0.2f);
        gsonPrint(g);
        assertTrue(floatEqual(g.getF(1).get(2), 0.3f));
    }

    @Test
    public void simpleCycleTest() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        gsonPrint(g);
        List<Integer> cycle = g.search(1,3);
        gsonPrint(cycle);
        assertEquals(cycle, Arrays.asList(1, 2, 3));
    }

    @Test
    public void longCycleTest() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(0, 2, 0.3f);
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        g.addEdge(3, 4, 0.2f);
        g.addEdge(3, 5, 0.3f);
        List<Integer> cycle = g.search(1,5);
        gsonPrint(cycle);
        assertEquals(cycle, Arrays.asList(1, 2, 3, 5));
    }


    @Test
    public void simpleCycleRemovalTest() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 1, 0.2f);
        assertTrue(floatEqual(g.getF(2).get(1), 0.1f));
        assertFalse(g.getF(1).containsKey(2));
    }

    public static AcyclicWeightedGraph<Integer> longCycleGraph() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(0, 2, 0.3f);
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        g.addEdge(3, 4, 0.2f);
        g.addEdge(3, 5, 0.3f);
        g.addEdge(5, 1, 0.4f);
        return g;
    }

    @Test
    public void longCycleRemovalTest() {
        AcyclicWeightedGraph<Integer> g = longCycleGraph();
        assertNull(g.getF(1).get(2));
        assertTrue(floatEqual(g.getF(2).get(3), 0.1f));
        assertTrue(floatEqual(g.getF(0).get(2), 0.3f));
        assertTrue(floatEqual(g.getF(5).get(1), 0.3f));
        assertTrue(floatEqual(g.getF(3).get(4), 0.2f));
    }

    @Test
    public void doubleCycleRemovalTest() {
        AcyclicWeightedGraph<Integer> g = new AcyclicWeightedGraph<>();
        g.addEdge(1,2,0.1f);
        g.addEdge(1,2,0.1f);
        g.addEdge(2,1,0.1f);
        g.addEdge(2,3,0.2f);
        g.addEdge(1,4,0.3f);
        g.addEdge(4, 3, 0.4f);
        g.addEdge(3, 1, 0.3f);
        assertNull(g.getF(3).get(1));
        gsonPrint(g);
    }

}
