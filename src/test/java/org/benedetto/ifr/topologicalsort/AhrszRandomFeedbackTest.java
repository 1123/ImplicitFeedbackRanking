package org.benedetto.ifr.topologicalsort;

import com.google.gson.Gson;
import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.consumers.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.consumers.FeedBackConsumer;
import org.benedetto.ifr.feedback.generators.ItemBiasedFeedBackGenerator;
import org.junit.Test;

public class AhrszRandomFeedbackTest {


    @Test
    public void testWithRandomFeedBack() throws InvalidExpansionStateException, InvalidAhrszStateException {
        ItemBiasedFeedBackGenerator generator = new ItemBiasedFeedBackGenerator(40, 2000, 5000, 1.0f);
        FeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        while (generator.hasNext()) {
            FeedBack<Integer> next = generator.next();
            System.err.println(new Gson().toJson(next));
            consumer.addFeedBack(next);
        }
    }


}
