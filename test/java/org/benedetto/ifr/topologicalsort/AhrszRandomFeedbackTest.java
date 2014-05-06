package org.benedetto.ifr.topologicalsort;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.hadoop.util.StringUtils;
import org.benedetto.ifr.feedback.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.FeedBackConsumer;
import org.benedetto.ifr.feedback.MaxAttractivityFeedbackGenerator;
import org.junit.Ignore;
import org.junit.Test;

public class AhrszRandomFeedbackTest {


    @Test
    @Ignore
    public void testWithRandomFeedBack() throws InvalidExpansionStateException, InvalidAhrszStateException {
        MaxAttractivityFeedbackGenerator generator = new MaxAttractivityFeedbackGenerator(40, 2000, 5000, 1.0f);
        FeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        while (generator.hasNext()) {
            FeedBack<Integer> next = generator.next();
            System.err.println(new Gson().toJson(next));
            consumer.addFeedBack(next);
        }
    }


}
