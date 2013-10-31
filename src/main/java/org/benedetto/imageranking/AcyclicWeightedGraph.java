package org.benedetto.imageranking;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/31/13
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class AcyclicWeightedGraph extends HashMapGraph {


    public AcyclicWeightedGraph() {
        super();
    }

    public void addEdge(int from, int to, float weight) {
        super.addEdge(from, to, weight);
        List<Integer> cycle = this.search(to, from);
        while (cycle != null) {
            this.removeCycle(cycle);
            cycle = this.search(to, from);
        }
    }

}

