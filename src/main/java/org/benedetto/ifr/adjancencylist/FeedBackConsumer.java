package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class FeedBackConsumer<N> {

    Map<Pair<Integer,Integer>, Integer> statistics;
    public AcyclicWeightedGraph<N> acyclicWeightedGraph;

    public FeedBackConsumer() {
        statistics = new HashMap<>();
        acyclicWeightedGraph = new AcyclicWeightedGraph<>();
    }

    private void addToStats(FeedBack feedBack) {
        int pageLength = feedBack.page.size();
        int chosenPosition = feedBack.page.indexOf(feedBack.chosen);
        Pair<Integer, Integer> clickPosition = new Pair<>(pageLength, chosenPosition);
        if (! this.statistics.containsKey(clickPosition)) {
            this.statistics.put(clickPosition, 1);
        } else {
            int oldCount = this.statistics.get(clickPosition);
            this.statistics.put(clickPosition, oldCount + 1);
        }
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
