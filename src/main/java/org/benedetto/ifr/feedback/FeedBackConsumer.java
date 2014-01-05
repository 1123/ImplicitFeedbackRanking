package org.benedetto.ifr.feedback;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 1/5/14
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public interface FeedBackConsumer<N> {

    public void addFeedBack(FeedBack<N> feedBack);

}
