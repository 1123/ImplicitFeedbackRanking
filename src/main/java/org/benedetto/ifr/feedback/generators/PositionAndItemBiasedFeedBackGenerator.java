package org.benedetto.ifr.feedback.generators;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PositionAndItemBiasedFeedBackGenerator implements FeedBackGenerator<Integer> {

    /*
     * We store a whole lot of intermediate results for efficiency purposes.
     */

    private int [][] distribution; // the distribution of clicks per page length and position
    int pageSums []; // for each l holds the sum of the distribution with length l
    private float [] pageProbabilities; // for each l, holds the probability that a page of length l is generated
    private float [] [] positionProbabilities;
    private float [] itemAttractiveness;
    private int items; // the number of items
    private int clicks; // the number of clicks to be generated
    Random r;

    public PositionAndItemBiasedFeedBackGenerator(
            int items, int clicks, final int [][] distribution
    ) {
        this.r = new Random();
        this.distribution = distribution;
        this.pageProbabilities = new float[distribution.length];
        this.items = items;
        this.clicks = clicks;
        this.pageSums = new int[distribution.length];
        this.initPageProbabilities();
        this.initPositionProbabilities();
        this.initItems();
    }

    /**
     * each item is given a random attractiveness between 0 and 1
     */
    private void initItems() {
        this.itemAttractiveness = new float[this.items];
        for (int i = 0; i < items; i++) {
            this.itemAttractiveness[i] = r.nextFloat();
        }
    }

    /**
     * for each page length l, the probability for a click on position i <= l
     * is computed from the distribution and saved.
     */
    private void initPositionProbabilities() {
        this.positionProbabilities = new float[this.distribution.length][this.distribution.length];
        for (int page = 0; page < this.pageSums.length; page++) {
            for (int position = 0; position < distribution[page].length; position++) {
                this.positionProbabilities[page][position] =
                        (float) this.distribution[page][position] / (float) this.pageSums[page];
            }
        }
    }

    /**
     * for each page length l, the probability for this page length is computed from the distribution.
     */

    private void initPageProbabilities() {
        int overallSum = 0;
        for (int i = 0; i < distribution.length; i++) {
            pageSums[i] = 0;
            for (int j = 0; j < distribution.length; j++) {
                pageSums[i] += distribution[i][j];
                overallSum += distribution[i][j];
            }
        }
        float accumulated = 0.0f;
        for (int i = 0; i < distribution.length; i++) {
            accumulated += (float) pageSums[i] / overallSum;
            pageProbabilities[i] = accumulated;
        }
    }

    @Override
    public boolean hasNext() {
        return (this.clicks > 0);
    }

    @Override
    public FeedBack<Integer> next() {
        int pageLength = this.randomPageLength();
        try {
            FeedBack<Integer> result = this.randomFeedBack(pageLength);
            this.clicks--;
            return result;
        } catch (InvalidFeedBackException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Integer> randomItems(int pageLength) {
        List<Integer> result = new ArrayList<>();
        while (result.size() < pageLength) {
            int candidate = r.nextInt(this.items);
            if (result.contains(candidate)) continue;
            result.add(candidate);
        }
        return result;
    }

    private FeedBack<Integer> randomFeedBack(int pageLength) throws InvalidFeedBackException {
        List<Integer> items = this.randomItems(pageLength);
        int chosen = -1;
        float maxAttractivity = 0;
        for (int i = 0; i < pageLength; i++) {
            float f = r.nextFloat();
            float attractivity = f + this.itemAttractiveness[items.get(i)] + this.positionProbabilities[pageLength - 1][i];
            if (attractivity > maxAttractivity) {
                chosen = items.get(i);
                maxAttractivity = attractivity;
            }
        }
        return new FeedBack<>(chosen, items);
    }


    /**
     *  a random page length is generated based on the precomputed page-probability
     */
    private int randomPageLength() {
        double pageRandom = Math.random();
        for (int i = 0; i < distribution.length; i++) {
            if (pageRandom < pageProbabilities[i]) {
                return i + 1;
            }
        }
        throw new RuntimeException("This should never happen!");
    }

    /**
     * need to be there, since we are implementing the iterator interface.
     */

    @Override
    public void remove() {
        throw new UnsupportedOperationException("this operation is not implemented yet.");
    }
}
