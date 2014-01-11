package org.benedetto.ifr.feedback;

import com.google.gson.Gson;
import org.benedetto.ifr.topologicalsort.Ahrsz;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

public class AhrszFeedBackConsumer<N extends Comparable<N>> implements FeedBackConsumer<N> {

    public Ahrsz<N> ahrsz;
    Gson gson = new Gson();

    public AhrszFeedBackConsumer() {
        ahrsz = new Ahrsz<>();
    }

    public void addFeedBack(String json) throws InvalidExpansionStateException, InvalidAhrszStateException {
        this.addFeedBack(gson.fromJson(json, FeedBack.class));
    }

    @Override
    public void addFeedBack(FeedBack<N> feedBack) throws InvalidExpansionStateException, InvalidAhrszStateException {
        System.err.println(new Gson().toJson(feedBack));
        float weight = 1.0f / feedBack.page.size();
        for (N item : feedBack.page) {
            if (item.equals(feedBack.chosen)) continue;
            this.ahrsz.addEdge(item, feedBack.chosen, weight);
        }
    }

}
