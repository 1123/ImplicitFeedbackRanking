package org.benedetto.ifr.feedback;

import java.util.*;


public class MaxAttractivityFeedbackGenerator implements FeedBackGenerator<Integer> {

    int items;
    int pageSize;
    int clicks;
    private int current;
    float randomness;

    public HashMap<Integer, Float> attractivity;

    /*
     * randomness is the ratio randomattractivity / attractivity.
     */

    public MaxAttractivityFeedbackGenerator(int pageSize, int clicks, int items, float randomness) {
        this.items = items;
        this.pageSize = pageSize;
        this.clicks = clicks;
        this.initalizeItems();
        this.current = 0;
        this.randomness = randomness;
    }

    private void initalizeItems() {
        this.attractivity = new HashMap<>();
        for (int i = 0; i < this.items; i++) {
            Random r = new Random();
            this.attractivity.put(i, r.nextFloat());
        }
    }

    class AttractivityComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return attractivity.get(o1).compareTo(attractivity.get(o2));
        }

    }

    public List<Integer> getItems() {
        return new ArrayList<>(attractivity.keySet());
    }

    public List<Integer> getSortedItems() {
        List<Integer> items = new ArrayList<>(attractivity.keySet());
        Collections.sort(items, new AttractivityComparator());
        return items;
    }

    @Override
    public boolean hasNext() {
        return current < clicks;
    }

    @Override
    public FeedBack<Integer> next() {
        if (current >= clicks) { throw new RuntimeException("No more Feedback to generate."); }
        this.current++;
        return randomIntFeedback(pageSize, items, attractivity, randomness);
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private static FeedBack<Integer> randomIntFeedback(
            int pageSize, int items, Map<Integer, Float> attractivity, float randomness) {
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
            float weight = attractivity.get(item) + randomness * r.nextFloat();
            if (weight > maxWeight) {
                result.chosen = item;
                maxWeight = weight;
            }
        }
        return result;
    }


}

