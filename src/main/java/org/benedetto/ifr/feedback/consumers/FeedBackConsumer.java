package org.benedetto.ifr.feedback.consumers;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.Statistics;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import java.util.List;

public interface FeedBackConsumer<N> {

    public void addFeedBack(FeedBack<N> feedBack) throws InvalidExpansionStateException, InvalidAhrszStateException;

    public Statistics getStatistics();

    void sort(List<N> toBeSorted);

}
