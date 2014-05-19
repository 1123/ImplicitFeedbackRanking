package org.benedetto.ifr.feedback.consumers;

import org.benedetto.ifr.adjancencylist.AcyclicWeightedGraph;
import org.benedetto.ifr.adjancencylist.ClosureGraph;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.Statistics;

import java.util.List;

public class AwgFeedBackConsumer<N extends Comparable<N>> implements FeedBackConsumer<N>{

    public Statistics statistics;
    public AcyclicWeightedGraph<N> acyclicWeightedGraph;

    public AwgFeedBackConsumer() {
        statistics = new Statistics();
        acyclicWeightedGraph = new AcyclicWeightedGraph<>();
    }

    public void addFeedBack(FeedBack<N> feedBack) {
        // Possibly the weight should be 1.0f / page.size() - 1.
        // The weight of the edges is in fact not important, as long as each click
        // sums up to the same weight.
        this.statistics.addToStats(feedBack);
        float weight = 1.0f / feedBack.getPage().size();
        for (N item : feedBack.getPage()) {
            if (item.equals(feedBack.getPage())) continue;
            this.acyclicWeightedGraph.addEdge(item, feedBack.getChosen(), weight);
        }
    }

    @Override
    public Statistics getStatistics() {
        return this.statistics;
    }

    @Override
    public void sort(List<N> toBeSorted) {
        ClosureGraph<N> closureGraph = new ClosureGraph<>(this.acyclicWeightedGraph);
        closureGraph.sort(toBeSorted);
    }

}
