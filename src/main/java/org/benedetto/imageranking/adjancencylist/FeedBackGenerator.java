package org.benedetto.imageranking.adjancencylist;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 11/2/13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class FeedBackGenerator implements Iterator<FeedBack> {


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

    public FeedBackGenerator(int pageSize, int clicks, int items) {
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
    public FeedBack next() {
        if (current >= clicks) { throw new RuntimeException("No more Feedback to generate."); }
        this.current++;
        return new FeedBack(pageSize, items, attractivity);
    }

    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}

