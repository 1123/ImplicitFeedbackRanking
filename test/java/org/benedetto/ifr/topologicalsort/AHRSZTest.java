package org.benedetto.ifr.topologicalsort;

import com.google.gson.Gson;
import org.benedetto.ifr.feedback.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static junit.framework.TestCase.assertTrue;

public class AhrszTest {

    @Test
    public void testSingleEdge() throws InvalidExpansionStateException, InvalidAhrszStateException {
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(1,2,0.1f);
        assert(ahrsz.before(1,2));
    }

    /**
     * A->B   C->D
     * ^------+
     */

    @Test
    public void testReorder() throws InvalidExpansionStateException, InvalidAhrszStateException {
        Ahrsz<Character> ahrsz = new Ahrsz<>();
        ahrsz.addEdge('A','B',0.1f);
        ahrsz.addEdge('C','D',0.1f);
        ahrsz.addEdge('C', 'A', 0.1f);
        assertTrue(ahrsz.before('A','B'));
        assertTrue(ahrsz.before('C','D'));
        assertTrue(ahrsz.before('C', 'A'));
    }

    @Test
    public void testSingleNodeCycle() throws InvalidExpansionStateException, InvalidAhrszStateException {
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(1, 1, 0.1f);
    }

    @Test
    public void testComplexGraph() throws InvalidExpansionStateException, InvalidAhrszStateException {
        Ahrsz<Character> ahrsz = new Ahrsz<>();
        ahrsz.addEdge('A','B',0.1f);
        ahrsz.addEdge('C','E',0.1f);
        ahrsz.addEdge('E','F',0.1f);
        ahrsz.addEdge('G', 'F', 0.1f);
        assertTrue(ahrsz.before('A', 'B'));
        assertTrue(ahrsz.before('C', 'E'));
        assertTrue(ahrsz.before('E', 'F'));
        assertTrue(ahrsz.before('G', 'F'));
        assertTrue(ahrsz.before('G', 'A'));
        ahrsz.addEdge('E', 'A', 0.1f);
        assertTrue(ahrsz.before('G', 'C'));
        assertTrue(ahrsz.before('C', 'E'));
        assertTrue(ahrsz.before('E', 'A'));
        assertTrue(ahrsz.before('A', 'B'));
        assertTrue(ahrsz.before('B', 'F'));
    }

    @Test
    public void testMultipleNodeCycle() throws InvalidExpansionStateException, InvalidAhrszStateException {
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(1, 2, 0.2f);
        ahrsz.addEdge(2, 3, 0.4f);
        ahrsz.addEdge(3, 4, 0.3f);
        assertTrue(ahrsz.before(1, 4));
        ahrsz.addEdge(4, 1, 0.5f);
        assertTrue(ahrsz.before(2, 3));
        assertTrue(ahrsz.before(3,4));
        assertTrue(ahrsz.before(4, 1));
    }

    @Test
    public void test() throws InvalidExpansionStateException, InvalidAhrszStateException {
        AhrszFeedBackConsumer<String> consumer = new AhrszFeedBackConsumer<>();
        consumer.addFeedBack("{\"chosen\":'B',\"page\":['A','B','C']}");
        consumer.addFeedBack("{\"chosen\":'A',\"page\":['A','C','B']}");
        assertTrue(consumer.ahrsz.before("C","A"));
        assertTrue(consumer.ahrsz.before("C", "B"));
        consumer.addFeedBack("{\"chosen\":'A',\"page\":['C','A','B']}");
        assertTrue(consumer.ahrsz.before("C","A"));
        assertTrue(consumer.ahrsz.before("C","B"));
        assertTrue(consumer.ahrsz.before("B", "A"));
        consumer.addFeedBack("{\"chosen\":'C',\"page\":['C','A','B']}");
        assertTrue(consumer.ahrsz.before("C", "A"));
        assertTrue(consumer.ahrsz.before("B", "A"));
    }

    @Test
    public void testWithFeedBack() throws InvalidExpansionStateException, InvalidAhrszStateException {
        AhrszFeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        consumer.addFeedBack("{\"chosen\":3,\"page\":[3,7,9]}");
        consumer.addFeedBack("{\"chosen\":9,\"page\":[7,3,9]}");
        consumer.addFeedBack("{\"chosen\":7,\"page\":[7,9,3]}");
    }

    @Test
    public void testWithRandomFeedBack() throws InvalidExpansionStateException, InvalidAhrszStateException {
        MaxAttractivityFeedbackGenerator generator = new MaxAttractivityFeedbackGenerator(40, 2000, 5000);
        FeedBackConsumer<Integer> consumer = new AhrszFeedBackConsumer<>();
        while (generator.hasNext()) {
            FeedBack<Integer> next = generator.next();
            System.err.println(new Gson().toJson(next));
            consumer.addFeedBack(next);
        }
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
    public void testSwitchPositions() throws InvalidExpansionStateException, InvalidAhrszStateException {
        Set<Integer> shiftUp = new TreeSet<>(Arrays.asList(1));
        Set<Integer> shiftDown = new TreeSet<>(Arrays.asList(0,5,8));
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(2,1,1f);
        ahrsz.addEdge(0,8,1f);
        ahrsz.addEdge(8,5,1f);
        assertTrue(ahrsz.before(2,1));
        assertTrue(ahrsz.before(0,8));
        assertTrue(ahrsz.before(8,5));
        ahrsz.switchPositions(shiftUp, shiftDown);
        assertTrue(ahrsz.before(0,8));
        assertTrue(ahrsz.before(5,1));
        assertTrue(ahrsz.before(8,5));
    }

    @Test
    public void testSwitchPositions2() throws InvalidAhrszStateException {
        Set<Integer> shiftUp = new TreeSet<>(Arrays.asList(10,15));
        Set<Integer> shiftDown = new TreeSet<>(Arrays.asList(2,4,5,19));
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.node2Index.put(15,-4);
        ahrsz.node2Index.put(5,-3);
        ahrsz.node2Index.put(4,0);
        ahrsz.node2Index.put(10,2);
        ahrsz.node2Index.put(19,3);
        ahrsz.node2Index.put(2,4);
        ahrsz.switchPositions(shiftUp, shiftDown);
        assertTrue(ahrsz.node2Index.get(5) == -4);
        assertTrue(ahrsz.node2Index.get(4) == -3);
        assertTrue(ahrsz.node2Index.get(19) == 0);
        assertTrue(ahrsz.node2Index.get(2) == 2);
        assertTrue(ahrsz.node2Index.get(15) == 3);
        assertTrue(ahrsz.node2Index.get(10) == 4);
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

