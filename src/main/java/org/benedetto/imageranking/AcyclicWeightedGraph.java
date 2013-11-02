package org.benedetto.imageranking;

import java.util.HashMap;
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
        while (cycle != null && this.get(from).containsKey(to) && this.get(from).get(to) > 0f) {
            this.removeCycle(cycle);
            cycle = this.search(to, from);
        }
    }

    public void addFeedBack(FeedBack feedBack) {
        float weight = 1.0f / feedBack.page.size();
        for (int item : feedBack.page) {
            if (item == feedBack.chosen) continue;
            this.addEdge(item, feedBack.chosen, weight);
        }
    }

    public HashMap<Integer, Float> ranking() {
        HashMap<Integer, Float> result = new HashMap<>();
        for (Integer start : this.keySet()) {
            if (result.containsKey(start)) continue;
            result.put(start, 0f);
            this.rankingRec(start, 0f, result);
        }
        return result;
    }

    // 1 \
    //    2
    // 0 /

    public void rankingRec(int start, float rank, HashMap<Integer, Float> result) {
        for (Integer next : this.get(start).keySet()) {
            if (result.containsKey(next)) continue;
            float newRank = rank + this.get(start).get(next);
            result.put(next, newRank);
            this.rankingRec(next, newRank, result);
        }
    }
}

