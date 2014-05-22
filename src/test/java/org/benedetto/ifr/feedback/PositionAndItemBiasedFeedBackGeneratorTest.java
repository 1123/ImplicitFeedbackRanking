package org.benedetto.ifr.feedback;

import com.google.gson.Gson;
import org.benedetto.ifr.feedback.generators.PositionAndItemBiasedFeedBackGenerator;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PositionAndItemBiasedFeedBackGeneratorTest {

    private static Gson gson = new Gson();

    @Test
    public void test() {
        PositionAndItemBiasedFeedBackGenerator generator =
                new PositionAndItemBiasedFeedBackGenerator(100, 100, new int[][] { { 1,0,0 }, { 1,1,0 }, { 3,2,1 } });
        while (generator.hasNext()) {
            FeedBack<Integer> feedBack = generator.next();
            checkForDuplicates(feedBack.getPage());
            System.err.println(gson.toJson(feedBack));
        }
    }

    private <T> void checkForDuplicates(List<T> input) {
        Set<T> set = new HashSet<T>(input);
        if (set.size() < input.size()) throw new RuntimeException("List contains duplicates.");
    }
}
