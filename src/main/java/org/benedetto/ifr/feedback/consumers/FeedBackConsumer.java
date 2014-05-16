package org.benedetto.ifr.feedback.consumers;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.Statistics;
import org.benedetto.ifr.flickr.PhotoDetails;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 1/5/14
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public interface FeedBackConsumer<N> {

    public void addFeedBack(FeedBack<N> feedBack) throws InvalidExpansionStateException, InvalidAhrszStateException;

    public Statistics getStatistics();

    void sort(List<N> toBeSorted);
}
