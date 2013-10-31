import com.google.gson.Gson;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/17/13
 * Time: 23:39
 */
public class ImageRankingTest {

    @Test
    public void test1() {
        DirectedWeightedGraph dwg = new DirectedWeightedGraph(4, 100);
        gsonPrint(dwg);
        System.err.println(dwg.outgoing(3));
    }

    /*
     *  0--0.1-->1--0.2-->2--0.3-->0
     */

    @Test
    public void testSimpleCycle() {
        DirectedWeightedGraph dwg = this.simpleCycleGraph();
        CycleRemover cycleRemover = new CycleRemover(dwg);
        cycleRemover.search();
        gsonPrint(dwg);
        assertTrue(dwg.get(0, 1) == 0f);
        assertTrue(dwg.get(1, 2) == 0.1f);
        assertTrue(Math.abs(dwg.get(2,0) - 0.2f) < 0.0001);
    }

    private DirectedWeightedGraph simpleCycleGraph() {
        DirectedWeightedGraph dwg = new DirectedWeightedGraph(5);
        dwg.addEdge(0, 1, 0.1f);
        dwg.addEdge(1, 2, 0.2f);
        dwg.addEdge(2, 0, 0.3f);
        return dwg;
    }

    private DirectedWeightedGraph complexCylceGrpah() {
        DirectedWeightedGraph dwg = simpleCycleGraph();
        dwg.addEdge(3, 0, 0.1f);
        dwg.addEdge(2, 4, 0.5f);
        return dwg;
    }

    @Test
    public void testCycle() {
        DirectedWeightedGraph dwg = this.complexCylceGrpah();
        CycleRemover cycleRemover = new CycleRemover(dwg);
        cycleRemover.search();
        assertTrue(dwg.get(0, 1) == 0f);
        assertTrue(dwg.get(1, 2) == 0.1f);
        assertTrue(dwg.get(2, 4) == 0.5f);
        gsonPrint(dwg);
    }


    // 0 --(0.6)--> 1 --(0.5)--> 2 --(0.3)--> 0
    // 0 --(0.3)--> 1 --(0.2)--> 2 --(0.5)--> 4 --(0.4)--> 3 --(0.3)--> 0
    // 0 --(0.1)--> 1            2 --(0.3)--> 4 --(0.2)--> 3 --(0.1)--> 0
    private DirectedWeightedGraph doubleCycleGraph() {
        DirectedWeightedGraph dwg = new DirectedWeightedGraph(5);
        dwg.addEdge(0, 1, 0.6f);
        dwg.addEdge(1, 2, 0.5f);
        dwg.addEdge(2, 0, 0.3f);
        dwg.addEdge(3, 0, 0.3f);
        dwg.addEdge(2, 4, 0.5f);
        dwg.addEdge(4, 3, 0.4f);
        return dwg;
    }

    private boolean floatEqual(float first, float second) {
        return Math.abs(first - second) < 0.0001;
    }

    @Test
    public void testDoubleCycle() {
        DirectedWeightedGraph dwg = this.doubleCycleGraph();
        CycleRemover cycleRemover = new CycleRemover(dwg);
        cycleRemover.search();
        assertTrue(floatEqual(dwg.get(0, 1), 0.1f));
        assertTrue(floatEqual(dwg.get(1, 2), 0.0f));
        assertTrue(floatEqual(dwg.get(2, 4), 0.3f));
        assertTrue(floatEqual(dwg.get(4, 3), 0.2f));
        assertTrue(floatEqual(dwg.get(3, 0), 0.1f));
        gsonPrint(dwg);
    }

    @Test
    public void test2() {
        DirectedWeightedGraph imageRanking = new DirectedWeightedGraph(4, new float[] {0.1f,0.2f,3.0f,4.0f,5.0f,6.0f});
        gsonPrint(imageRanking);
    }

    public void gsonPrint(Object o) {
        System.err.println(new Gson().toJson(o));
    }
}
