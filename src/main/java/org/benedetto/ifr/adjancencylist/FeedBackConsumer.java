package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.ComparablePair;

import java.util.HashMap;
import java.util.Map;

public class FeedBackConsumer<N> {

    public Map<Integer, Map<Integer, Integer>> statistics;
    public AcyclicWeightedGraph<N> acyclicWeightedGraph;

    public FeedBackConsumer() {
        statistics = new HashMap<>();
        acyclicWeightedGraph = new AcyclicWeightedGraph<>();
    }

    private void addToStats(FeedBack feedBack) {
        int pageLength = feedBack.page.size();
        int chosenPosition = feedBack.page.indexOf(feedBack.chosen) + 1;
        if (! this.statistics.containsKey(pageLength)) {
            this.statistics.put(pageLength, new HashMap<Integer,Integer>());
        }
        if (! this.statistics.get(pageLength).containsKey(chosenPosition)) {
            this.statistics.get(pageLength).put(chosenPosition, 0);
        }
        int oldValue = this.statistics.get(pageLength).get(chosenPosition);
        this.statistics.get(pageLength).put(chosenPosition, oldValue + 1);
    }

    public void addFeedBack(FeedBack<N> feedBack) {
        // Possibly the weight should be 1.0f / page.size() - 1.
        // The weight of the edges is in fact not important, as long as each click
        // sums up to the same weight.
        this.addToStats(feedBack);
        float weight = 1.0f / feedBack.page.size();
        for (N item : feedBack.page) {
            if (item.equals(feedBack.chosen)) continue;
            this.acyclicWeightedGraph.addEdge(item, feedBack.chosen, weight);
        }
    }

}
