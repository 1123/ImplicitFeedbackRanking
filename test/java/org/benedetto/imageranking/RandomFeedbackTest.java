package org.benedetto.imageranking;

import org.junit.Test;

import static org.benedetto.imageranking.TestUtil.gsonPrint;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 11/2/13
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class RandomFeedbackTest {

    @Test
    public void test() {
        FeedBackGenerator feedBackGenerator = new FeedBackGenerator();
        gsonPrint(feedBackGenerator.attractivity);
        while (feedBackGenerator.hasNext()) {
            gsonPrint(feedBackGenerator.next());
        }
    }
}
