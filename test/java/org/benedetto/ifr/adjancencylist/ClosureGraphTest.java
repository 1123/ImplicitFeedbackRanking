package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.TestUtil;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 11/3/13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class ClosureGraphTest {

    @Test
    public void simpleTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1,2,0.2f);
        g.addEdge(2,3,0.3f);
        g.addEdge(2,4,0.3f);
        ClosureGraph cg = new ClosureGraph(g);
        TestUtil.gsonPrint(cg);
        assertTrue(cg.size() == 5);
    }

    @Test
    public void mediumTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1,4, 0.1f);
        g.addEdge(2,3, 0.2f);
        g.addEdge(3,4, 0.1f); // 2->4
        g.addEdge(1,3, 0.1f); // 1->4
        ClosureGraph cg = new ClosureGraph(g);
        TestUtil.gsonPrint(g);
        TestUtil.gsonPrint(cg);
        assertTrue(cg.size() == 5);
    }

    @Test
    public void test() {
        AcyclicWeightedGraph g = AcyclicWeightedGraphTest.longCycleGraph();
        ClosureGraph cg = new ClosureGraph(g);
        //TestUtil.gsonPrint(g);
        TestUtil.gsonPrint(cg);
    }

}
