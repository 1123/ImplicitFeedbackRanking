package org.benedetto.ifr.feedback;

import java.util.HashMap;
import java.util.Map;

/*
 * This class is used to maintain the number of clicks by page size and position.
 */

public class Statistics extends HashMap<Integer, Map<Integer, Integer>> {

    public  void addToStats(FeedBack feedBack) {
        int pageLength = feedBack.getPage().size();
        int chosenPosition = feedBack.getPage().indexOf(feedBack.getChosen()) + 1;
        if (! this.containsKey(pageLength)) {
            this.put(pageLength, new HashMap<Integer,Integer>());
        }
        if (! this.get(pageLength).containsKey(chosenPosition)) {
            this.get(pageLength).put(chosenPosition, 0);
        }
        int oldValue = this.get(pageLength).get(chosenPosition);
        this.get(pageLength).put(chosenPosition, oldValue + 1);
    }

}
