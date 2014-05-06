package org.benedetto.ifr.topologicalsort;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import org.benedetto.ifr.feedback.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.FeedBackConsumer;
import org.benedetto.ifr.feedback.MaxAttractivityFeedbackGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.ContiguousSet.*;

public class EfficiencyTest {

    @Test
    public void test1() throws InvalidExpansionStateException, InvalidAhrszStateException {
        final int minItems = 100;
        final int itemsStep = 10;
        final int numItemMeasurements = 20;
        final int minClicks = 100;
        final int clicksStep = 100;
        final int numClickMeasurements = 20;
        final int iterations = 10;
        int [][] measurements = new int[numClickMeasurements][numItemMeasurements];
        for (int i = 0; i < numClickMeasurements; i++) {
            int clicks = minClicks + i * clicksStep;
            for (int j = 0; j < numItemMeasurements; j++) {
                int items = minItems + j * itemsStep;
                int sum = 0;
                for (int iteration = 0; iteration < iterations; iteration++) {
                    MaxAttractivityFeedbackGenerator generator = new MaxAttractivityFeedbackGenerator(40, clicks, items, 1.0f);
                    FeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
                    while (generator.hasNext()) {
                        consumer.addFeedBack(generator.next());
                    }
                    List<Integer> consumerSortedItems = generator.getItems();
                    consumer.sort(consumerSortedItems);
                    sum += SortDistance.distance(generator.getSortedItems(), consumerSortedItems);
                }
                measurements[i][j] = sum / iterations;
                System.err.println(sum / iterations);
            }
        }
    }

    @Test
    public void testRandomDistance() {
        int sum = 0;
        for (int i = 0; i < 200; i++) {
            List<Integer> list1 = create(Range.closed(1, 1000), DiscreteDomain.integers()).asList();
            List<Integer> list2 = new ArrayList<>(create(Range.closed(1, 1000), DiscreteDomain.integers()).asList());
            Collections.shuffle(list2);
            sum += SortDistance.distance(list1, list2);
        }
        System.err.println(sum / 200.0);
        // Expected value is 1/3 * L * L = 1/3 * 1000 * 1000 = 333333
    }
}
