package org.benedetto.ifr.adjancencylist;

import java.util.HashMap;
import java.util.List;


public class AcyclicWeightedGraph<N> extends HashMapGraph<N> {

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

    public void addFeedBack(FeedBack<N> feedBack) {
        // Possibly the weight should be 1.0f / page.size() - 1.
        // The weight of the edges is in fact not important, as long as each click
        // sums up to the same weight.
        float weight = 1.0f / feedBack.page.size();
        for (N item : feedBack.page) {
            if (item.equals(feedBack.chosen)) continue;
            this.addEdge(item, feedBack.chosen, weight);
        }
    }

    /**
     * This method returns a topological sort of the graph. This implementation seems to be false.
     * The recursion should only start at root nodes.
     * @return
     */

    public HashMap<N, Float> ranking() {
        HashMap<N, Float> result = new HashMap<>();
        for (N start : this.nodes()) {
            if (result.containsKey(start)) continue;
            result.put(start, 0f);
            this.rankingRec(start, 0f, result);
        }
        return result;
    }

    // 1 \       0 - 1 - 2
    //    2       \        \
    // 0 /         \------- 3

    public void rankingRec(N start, float rank, HashMap<N, Float> result) {
        for (N next : this.getF(start).keySet()) {
            if (result.containsKey(next)) continue;
            float newRank = rank + this.getF(start).get(next);
            result.put(next, newRank);
            this.rankingRec(next, newRank, result);
        }
    }
}

