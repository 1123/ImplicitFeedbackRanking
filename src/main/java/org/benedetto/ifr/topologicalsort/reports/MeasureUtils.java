package org.benedetto.ifr.topologicalsort.reports;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.consumers.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.consumers.FeedBackConsumer;
import org.benedetto.ifr.feedback.generators.ItemBiasedFeedBackGenerator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

public class MeasureUtils {

    public static void consume(ItemBiasedFeedBackGenerator generator)
            throws InvalidExpansionStateException, InvalidAhrszStateException {
        FeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        while (generator.hasNext()) {
            FeedBack<Integer> next = generator.next();
            consumer.addFeedBack(next);
        }
    }

    public static float measure(AhrszParams ahrszParams) throws InvalidExpansionStateException, InvalidAhrszStateException {
        long diff = 0;
        for (int i = 0; i < ahrszParams.iterations; i++) {
            ItemBiasedFeedBackGenerator generator =
                    new ItemBiasedFeedBackGenerator(ahrszParams.pageSize, ahrszParams.clicks, ahrszParams.items, ahrszParams.randomness);
            long start = System.currentTimeMillis();
            MeasureUtils.consume(generator);
            long end = System.currentTimeMillis();
            diff += (end - start);
        }
        return diff / ahrszParams.iterations;
    }

}

