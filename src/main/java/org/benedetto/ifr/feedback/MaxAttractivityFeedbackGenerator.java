package org.benedetto.ifr.feedback;

import java.util.*;


public class MaxAttractivityFeedbackGenerator implements FeedBackGenerator<Integer> {

    int items;
    int pageSize;
    int clicks;
    private int current;

    public HashMap<Integer, Float> attractivity;

    private void initalizeItems() {
        this.attractivity = new HashMap<>();
        for (int i = 0; i < this.items; i++) {
            Random r = new Random();
            this.attractivity.put(i, r.nextFloat());
        }
    }

    public MaxAttractivityFeedbackGenerator(int pageSize, int clicks, int items) {
        this.items = items;
        this.pageSize = pageSize;
        this.clicks = clicks;
        this.initalizeItems();
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < clicks;
    }

    @Override
    public FeedBack<Integer> next() {
        if (current >= clicks) { throw new RuntimeException("No more Feedback to generate."); }
        this.current++;
        return randomIntFeedback(pageSize, items, attractivity);
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private static FeedBack<Integer> randomIntFeedback(int pageSize, int items, Map<Integer, Float> attractivity) {
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

