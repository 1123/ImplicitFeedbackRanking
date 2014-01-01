package org.benedetto.ifr.adjancencylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FeedBack<N> {

    public N chosen;
    public List<N> page;

    public FeedBack() { }

    public FeedBack(N chosen, List<N> page) {
        this.chosen = chosen;
        this.page = page;
    }

    public static FeedBack<Integer> randomIntFeedback(int pageSize, int items, Map<Integer, Float> attractivity) {
        FeedBack<Integer> result = new FeedBack<>();
        if (pageSize > items) throw new RuntimeException("Pagesize must be smaller or equal to the number of items.");
        result.page = new ArrayList<>();
        Random r = new Random();
        result.chosen = 0;
        float maxWeight = 0f;
        while (result.page.size() < pageSize) {
            int item = r.nextInt(items);
            if (result.page.contains(item)) continue;
            result.page.add(item);
            float weight = attractivity.get(item) + r.nextFloat();
            if (weight > maxWeight) {
                result.chosen = item;
                maxWeight = weight;
            }
        }
        return result;
    }

}
