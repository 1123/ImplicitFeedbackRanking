package org.benedetto.ifr.adjancencylist;

import java.util.HashMap;
import java.util.List;

public class AcyclicWeightedGraph<N extends Comparable<N>> extends HashMapGraph<N> {

    public AcyclicWeightedGraph() {
        super();
    }

    public void addEdge(N from, N to, float weight) {
        super.addEdge(from, to, weight);
        List<N> cycle = this.search(to, from);
        while (cycle != null && this.getF(from).containsKey(to) && this.getF(from).get(to) > 0f) {
            this.removeCycle(cycle);
            cycle = this.search(to, from);
        }
    }

    /**
     * This method returns a topological sort of the graph. This implementation seems to be false.
     * Assignment of weights to nodes may be false in the presence of multiple paths from a node n
     * to a node m.
     */

    public HashMap<N, Float> ranking() {
        HashMap<N, Float> result = new HashMap<>();
        for (N start : this.forward.keySet()) {
            if (this.backward.containsKey(start)) continue; // only start at root nodes.
            if (result.containsKey(start)) continue; // node has already been visited.
            result.put(start, 0f); // root nodes get weight 0
            this.rankingRec(start, 0f, result); // recursive call. This will manipulate result.
        }
        return result;
    }

    // 1 \       0 - 1 - 2
    //    2       \        \
    // 0 /         \------- 3

    public void rankingRec(N start, float rank, HashMap<N, Float> result) {
        if (this.getF(start) == null) return;
        for (N next : this.getF(start).keySet()) {
            if (result.containsKey(next)) continue;
            float newRank = rank + this.getF(start).get(next);
            result.put(next, newRank);
            this.rankingRec(next, newRank, result);
        }
    }

}

