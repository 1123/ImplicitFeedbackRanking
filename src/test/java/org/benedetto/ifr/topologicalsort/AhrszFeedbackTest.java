package org.benedetto.ifr.topologicalsort;

import org.benedetto.ifr.feedback.FeedBack;
import org.benedetto.ifr.feedback.InvalidFeedBackException;
import org.benedetto.ifr.feedback.consumers.AhrszFeedBackConsumer;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class AhrszFeedbackTest {

    @Test
    public void testWithFeedBack() throws InvalidExpansionStateException, InvalidAhrszStateException {
        AhrszFeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        consumer.addFeedBack("{\"chosen\":3,\"page\":[3,7,9]}");
        consumer.addFeedBack("{\"chosen\":9,\"page\":[7,3,9]}");
        consumer.addFeedBack("{\"chosen\":7,\"page\":[7,9,3]}");
    }


    @Test
    public void testWithMoreFeedBack() throws InvalidExpansionStateException, InvalidAhrszStateException {
        AhrszFeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        consumer.addFeedBack("{\"chosen\":17,\"page\":[12,4,17,1,18,15,34,7,25,37]}");
        consumer.addFeedBack("{\"chosen\":38,\"page\":[38,20,14,39,21,25,12,24,31,7]}");
        consumer.addFeedBack("{\"chosen\":7,\"page\":[7,8,1,16,37,36,31,12,19,2]}");
        consumer.addFeedBack("{\"chosen\":36,\"page\":[23,36,0,6,2,19,11,28,21,31]}");
        consumer.addFeedBack("{\"chosen\":15,\"page\":[3,23,29,4,1,35,9,32,7,15]}");
        consumer.addFeedBack("{\"chosen\":38,\"page\":[35,9,5,15,0,14,2,38,6,10]}");
        consumer.addFeedBack("{\"chosen\":17,\"page\":[16,33,30,17,14,37,24,15,12,5]}");
        consumer.addFeedBack("{\"chosen\":1,\"page\":[26,2,6,20,16,34,30,7,1,4]}");
        consumer.addFeedBack("{\"chosen\":1,\"page\":[25,21,11,6,1,9,34,14,12,0]}");
        consumer.addFeedBack("{\"chosen\":1,\"page\":[37,38,18,7,36,30,11,39,1,10]}");
    }

    @Test
    public void anotherTest() throws InvalidFeedBackException, InvalidExpansionStateException, InvalidAhrszStateException {
        AhrszFeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        consumer.addFeedBack(new FeedBack<>(8, Arrays.asList(0, 2, 9, 6, 13, 3, 4, 1, 17, 8)));
        assertTrue(consumer.ahrsz.node2Index.get(17) == -7);
        consumer.addFeedBack(new FeedBack<>(3, Arrays.asList(11, 6, 19, 16, 14, 3, 7, 1, 8, 17)));
        assertTrue(consumer.ahrsz.node2Index.get(3) == -2);
        assertTrue(consumer.ahrsz.node2Index.get(6) == -4);
        assertTrue(consumer.ahrsz.node2Index.get(7) == -12);
        consumer.addFeedBack(new FeedBack<>(5, Arrays.asList(7, 5, 13, 2, 10, 12, 0, 1, 9, 8)));
        assertTrue(consumer.ahrsz.node2Index.get(10) == -13);
        assertTrue(consumer.ahrsz.node2Index.get(12) == -14);
        consumer.addFeedBack(new FeedBack<>(1, Arrays.asList(7, 15, 5, 12, 19, 6, 1, 4, 3, 2)));
        consumer.addFeedBack(new FeedBack<>(1, Arrays.asList(13, 17, 19, 1, 16, 2, 8, 10, 11, 5)));
    }

    @Test
    public void oneMoreTest() throws InvalidFeedBackException, InvalidExpansionStateException, InvalidAhrszStateException {
        AhrszFeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        consumer.addFeedBack(new FeedBack<>(10,Arrays.asList(6,4,10,0,18,5,14,11,15,16)));
        consumer.addFeedBack(new FeedBack<>(10,Arrays.asList(6,4,10,0,18,5,14,11,15,16)));
        consumer.addFeedBack(new FeedBack<>(2,Arrays.asList(19,8,5,14,12,7,2,1,17,4)));
        consumer.addFeedBack(new FeedBack<>(2,Arrays.asList(19,8,5,14,12,7,2,1,17,4)));
        consumer.addFeedBack(new FeedBack<>(15,Arrays.asList(14,2,6,7,15,12,8,4,11,0)));
        consumer.addFeedBack(new FeedBack<>(15,Arrays.asList(14,2,6,7,15,12,8,4,11,0)));
    }

}

