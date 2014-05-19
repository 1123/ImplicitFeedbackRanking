package org.benedetto.ifr.feedback.generators;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;

import java.util.*;


public class ItemBiasedFeedBackGenerator implements FeedBackGenerator<Integer> {

    int items;
    int pageSize;
    int clicks;
    private int current;
    float randomness;

    public HashMap<Integer, Float> attractivity;

    /*
     * randomness is the ratio randomattractivity / attractivity.
     */

    public ItemBiasedFeedBackGenerator(int pageSize, int clicks, int items, float randomness) {
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
        try {
            return randomIntFeedback(pageSize, items, attractivity, randomness);
        } catch (InvalidFeedBackException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private static FeedBack<Integer> randomIntFeedback(
            int pageSize, int items, Map<Integer, Float> attractivity, float randomness) throws InvalidFeedBackException {
        if (pageSize > items) throw new RuntimeException("Pagesize must be smaller or equal to the number of items.");
        List<Integer> page = new ArrayList<>();
        Random r = new Random();
        int chosen = 0;
        float maxWeight = 0f;
        while (page.size() < pageSize) {
            int item = r.nextInt(items);
            if (page.contains(item)) continue;
            page.add(item);
            float weight = attractivity.get(item) + randomness * r.nextFloat();
            if (weight > maxWeight) {
                chosen = item;
                maxWeight = weight;
            }
        }
        FeedBack<Integer> result = new FeedBack<>(chosen, page);
        return result;
    }


}

