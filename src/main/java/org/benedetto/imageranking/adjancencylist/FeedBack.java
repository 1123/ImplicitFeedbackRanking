package org.benedetto.imageranking.adjancencylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FeedBack {

    int chosen;
    List<Integer> page;

    public FeedBack(int chosen, List<Integer> page) {
        this.chosen = chosen;
        this.page = page;
    }

    public FeedBack(int pageSize, int items, Map<Integer, Float> attractivity) {
        if (pageSize > items) throw new RuntimeException("Pagesize must be smaller or equal to the number of items.");
        this.page = new ArrayList<>();
        Random r = new Random();
        this.chosen = 0;
        float maxWeight = 0f;
        while (page.size() < pageSize) {
            int item = r.nextInt(items);
            if (page.contains(item)) continue;
            this.page.add(item);
            float weight = attractivity.get(item) + r.nextFloat();
            if (weight > maxWeight) {
                this.chosen = item;
                maxWeight = weight;
            }
        }
    }

}
