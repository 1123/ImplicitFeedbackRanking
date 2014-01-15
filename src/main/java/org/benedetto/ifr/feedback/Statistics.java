package org.benedetto.ifr.feedback;

import java.util.HashMap;
import java.util.Map;

public class Statistics extends HashMap<Integer, Map<Integer, Integer>> {

    public  void addToStats(FeedBack feedBack) {
        int pageLength = feedBack.page.size();
        int chosenPosition = feedBack.page.indexOf(feedBack.chosen) + 1;
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
