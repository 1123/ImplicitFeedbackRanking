package org.benedetto.ifr.topologicalsort.reports;

import org.benedetto.ifr.feedback.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.FeedBackConsumer;
import org.benedetto.ifr.feedback.MaxAttractivityFeedbackGenerator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;

public class MeasureUtils {

    public static void consume(MaxAttractivityFeedbackGenerator generator)
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
            MaxAttractivityFeedbackGenerator generator =
                    new MaxAttractivityFeedbackGenerator(ahrszParams.pageSize, ahrszParams.clicks, ahrszParams.items, ahrszParams.randomness);
            long start = System.currentTimeMillis();
            MeasureUtils.consume(generator);
            long end = System.currentTimeMillis();
            diff += (end - start);
        }
        return diff / ahrszParams.iterations;
    }

}

