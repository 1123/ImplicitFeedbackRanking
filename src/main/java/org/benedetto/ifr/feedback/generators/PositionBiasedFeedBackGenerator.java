package org.benedetto.ifr.feedback.generators;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class generates feedback based on the following assumptions:
 *
 * + A page contains exactly 10 search items
 * + the relative attractiveness of the positions is according to the array positionAttractivity
 * + all items are equally attractive
 *
 * The number of feedbacks to generate and the number of items can be passed in to the generator.
 */

public class PositionBiasedFeedBackGenerator implements FeedBackGenerator<Integer> {

    int amount;
    int items;
    static final int [] positionAttractivity = new int[]{ 9, 8, 7, 6, 6, 5, 5, 5, 6, 7 };
    static float [] positionAttractivityNormalized = new float[positionAttractivity.length];
    // intervals are used for generating the Feedback.
    // A random number between 0 and 1 is picked, then the interval is found for the number.
    static float [] positionAttractivityIntervals = new float[positionAttractivity.length];

    public PositionBiasedFeedBackGenerator(int amount, int items) {
        this.amount = amount;
        this.items = items;
        this.initializeAttractivity();
    }

    private void initializeAttractivity() {
        int sum = 0;
        for (int i = 0; i < positionAttractivity.length; i++) {
            sum += positionAttractivity[i];
        }
        for (int i = 0; i < positionAttractivity.length; i++) {
            positionAttractivityNormalized[i] = (1.0f + positionAttractivity[i]) / sum;
            positionAttractivityIntervals[i] =
                    ((i == 0) ? 0.0f : positionAttractivityIntervals[i-1]) + positionAttractivityNormalized[i];
        }
    }

    @Override
    public boolean hasNext() {
        return amount > 0;
    }

    @Override
    public FeedBack<Integer> next() {
        int chosen = this.randomPosition();
        List<Integer> page = new ArrayList<>();
        while (page.size() < positionAttractivity.length) {
            int item = (int) (Math.random() * items);
            if (page.contains(item)) {
                continue;
            }
            page.add(item);
        }
        amount--;
        try {
            return new FeedBack<>(page.get(chosen), page);
        } catch (InvalidFeedBackException e) {
            throw new RuntimeException(e);
        }
    }

    private int randomPosition() {
        float r = (float) Math.random();
        for (int position = 0; position < positionAttractivity.length; position++) {
            if (r < positionAttractivityIntervals[position]) return position;
        }
        throw new RuntimeException("This should never happen :-)");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("not implemented.");
    }
}
