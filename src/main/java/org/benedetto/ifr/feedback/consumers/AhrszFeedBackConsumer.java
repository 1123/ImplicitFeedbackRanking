package org.benedetto.ifr.feedback.consumers;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.Statistics;
import org.benedetto.ifr.topologicalsort.AhrszAlgorithm;
import org.benedetto.ifr.topologicalsort.IndexComparator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import java.util.Collections;
import java.util.List;

public class AhrszFeedBackConsumer<N extends Comparable<N>> implements FeedBackConsumer<N> {

    public AhrszAlgorithm<N> ahrsz;
    Gson gson = new Gson();
    Statistics statistics;

    public AhrszFeedBackConsumer() {
        ahrsz = new AhrszAlgorithm<>();
        statistics = new Statistics();
    }

    public void addFeedBack(String json) throws InvalidExpansionStateException, InvalidAhrszStateException {
        this.addFeedBack(gson.fromJson(json, FeedBack.class));
    }

    @Override
    public void addFeedBack(FeedBack<N> feedBack) throws InvalidExpansionStateException, InvalidAhrszStateException {
        this.statistics.addToStats(feedBack);
        float weight = 1.0f / feedBack.page.size();
        for (N item : feedBack.page) {
            if (item.equals(feedBack.chosen)) continue;
            this.ahrsz.addEdge(item, feedBack.chosen, weight);
        }
    }

    @Override
    public Statistics getStatistics() {
        return this.statistics;
    }

    @Override
    public void sort(List<N> toBeSorted) {
        Collections.sort(toBeSorted, new IndexComparator<N>(this.ahrsz.node2Index));
        Lists.reverse(toBeSorted); // sort descending
    }

}
