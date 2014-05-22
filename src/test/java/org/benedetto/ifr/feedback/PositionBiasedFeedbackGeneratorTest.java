package org.benedetto.ifr.feedback;

import com.google.gson.Gson;
import org.benedetto.ifr.feedback.generators.PositionBiasedFeedBackGenerator;
import org.junit.Test;

public class PositionBiasedFeedbackGeneratorTest {

    static final Gson gson = new Gson();

    @Test
    public void smokeTest() {
        // TODO: we may want to add assertions on page length and uniqueness.
        PositionBiasedFeedBackGenerator generator = new PositionBiasedFeedBackGenerator(10,20);
        while (generator.hasNext()) {
            FeedBack<Integer> feedBack = generator.next();
            System.err.println(gson.toJson(feedBack));
        }
    }
}
