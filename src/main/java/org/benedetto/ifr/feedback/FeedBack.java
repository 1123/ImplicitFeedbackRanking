package org.benedetto.ifr.feedback;

import java.util.List;

public class FeedBack<N> {

    /*
     * This class generates Clicks (FeedBack) under the assumption that each item has an inherent attractivity.
     * This generator selects those items where attractivity(item) + random(0,1) is maximal.
     * Many other selection criteria are possible.
     * Items with higher attractivity have a greater chance of being clicked than items with low attractivity.
     */

    public N chosen;
    public List<N> page;

    public FeedBack() { }

    public FeedBack(N chosen, List<N> page) throws InvalidFeedBackException {
        if (! page.contains(chosen)) throw new InvalidFeedBackException();
        this.chosen = chosen;
        this.page = page;
    }


}

