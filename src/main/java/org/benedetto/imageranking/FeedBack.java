package org.benedetto.imageranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FeedBack {

    int chosen;
    List<Integer> page;

    public FeedBack(int pageSize, int items, Map<Integer, Float> attractivity) {
        this.page = new ArrayList<>();
        Random r = new Random();
        this.chosen = 0;
        float maxWeight = 0f;
        for (int i = 0; i < pageSize; i++) {
            int item = r.nextInt(items);
            this.page.add(item);
            float weight = attractivity.get(item) + r.nextFloat();
            if (weight > maxWeight) {
                this.chosen = item;
                maxWeight = weight;
            }
        }
    }

}
