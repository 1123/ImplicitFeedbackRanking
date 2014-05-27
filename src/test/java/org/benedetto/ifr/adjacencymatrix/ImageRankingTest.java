package org.benedetto.ifr.adjacencymatrix;

import org.benedetto.ifr.TestUtil;
import org.benedetto.ifr.util.FloatUtils;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class ImageRankingTest {

    @Test
    public void test1() {
        AdjacencyGraph dwg = new AdjacencyGraph(4, 100);
        TestUtil.gsonPrint(dwg);
        System.err.println(dwg.outgoing(3));
    }

    /*
     *  0--0.1-->1--0.2-->2--0.3-->0
     */

    @Test
    public void testSimpleCycle() {
        AdjacencyGraph dwg = this.simpleCycleGraph();
        CycleRemover cycleRemover = new CycleRemover(dwg);
        cycleRemover.search();
        TestUtil.gsonPrint(dwg);
        assertTrue(dwg.get(0, 1) == 0f);
        assertTrue(dwg.get(1, 2) == 0.1f);
        assertTrue(Math.abs(dwg.get(2,0) - 0.2f) < 0.0001);
    }

    private AdjacencyGraph simpleCycleGraph() {
        AdjacencyGraph dwg = new AdjacencyGraph(5);
        dwg.addEdge(0, 1, 0.1f);
        dwg.addEdge(1, 2, 0.2f);
        dwg.addEdge(2, 0, 0.3f);
        return dwg;
    }

    private AdjacencyGraph complexCylceGrpah() {
        AdjacencyGraph dwg = simpleCycleGraph();
        dwg.addEdge(3, 0, 0.1f);
        dwg.addEdge(2, 4, 0.5f);
        return dwg;
    }

    @Test
    public void testCycle() {
        AdjacencyGraph dwg = this.complexCylceGrpah();
        CycleRemover cycleRemover = new CycleRemover(dwg);
        cycleRemover.search();
        assertTrue(dwg.get(0, 1) == 0f);
        assertTrue(dwg.get(1, 2) == 0.1f);
        assertTrue(dwg.get(2, 4) == 0.5f);
        TestUtil.gsonPrint(dwg);
    }


    // 0 --(0.6)--> 1 --(0.5)--> 2 --(0.3)--> 0
    // 0 --(0.3)--> 1 --(0.2)--> 2 --(0.5)--> 4 --(0.4)--> 3 --(0.3)--> 0
    // 0 --(0.1)--> 1            2 --(0.3)--> 4 --(0.2)--> 3 --(0.1)--> 0
    private AdjacencyGraph doubleCycleGraph() {
        AdjacencyGraph dwg = new AdjacencyGraph(5);
        dwg.addEdge(0, 1, 0.6f);
        dwg.addEdge(1, 2, 0.5f);
        dwg.addEdge(2, 0, 0.3f);
        dwg.addEdge(3, 0, 0.3f);
        dwg.addEdge(2, 4, 0.5f);
        dwg.addEdge(4, 3, 0.4f);
        return dwg;
    }


    @Test
    public void testDoubleCycle() {
        AdjacencyGraph dwg = this.doubleCycleGraph();
        CycleRemover cycleRemover = new CycleRemover(dwg);
        cycleRemover.search();
        assertTrue(FloatUtils.floatEqual(dwg.get(0, 1), 0.1f));
        assertTrue(FloatUtils.floatEqual(dwg.get(1, 2), 0.0f));
        assertTrue(FloatUtils.floatEqual(dwg.get(2, 4), 0.3f));
        assertTrue(FloatUtils.floatEqual(dwg.get(4, 3), 0.2f));
        assertTrue(FloatUtils.floatEqual(dwg.get(3, 0), 0.1f));
        TestUtil.gsonPrint(dwg);
    }

    @Test
    public void test2() {
        AdjacencyGraph imageRanking = new AdjacencyGraph(4, new float[] {0.1f,0.2f,3.0f,4.0f,5.0f,6.0f});
        TestUtil.gsonPrint(imageRanking);
    }

}

