package org.benedetto.ifr.springy;

import org.benedetto.ifr.adjancencylist.HashMapGraph;
import org.benedetto.ifr.feedback.consumers.AhrszFeedBackConsumer;
import org.benedetto.ifr.feedback.consumers.FeedBackConsumer;
import org.benedetto.ifr.feedback.generators.PositionAndItemBiasedFeedBackGenerator;
import org.benedetto.ifr.topologicalsort.InvalidAhrszStateException;
import org.benedetto.ifr.topologicalsort.InvalidExpansionStateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SpringyTranslatorTest {

    @Autowired
    SpringyTranslator<Integer> translator;

    @Test
    public void test() throws FileNotFoundException {
        HashMapGraph<Integer> hashMapGraph = new HashMapGraph<>();
        hashMapGraph.addEdge(1,3,0.1f);
        hashMapGraph.addEdge(2,4,0.2f);
        hashMapGraph.addEdge(1,4,0.3f);
        hashMapGraph.addEdge(2,5,0.2f);
        translator.serializeToHtml(hashMapGraph, 10, "src/main/resources/graph.html");
    }

    @Test
    public void test2() throws InvalidExpansionStateException, InvalidAhrszStateException, FileNotFoundException {
        PositionAndItemBiasedFeedBackGenerator generator =
                new PositionAndItemBiasedFeedBackGenerator(100, 200, new int[][]{ { 4,3,2,1}, {2,1,1,0}, {1,1,0,0}, {1,0,0,0} });
        AhrszFeedBackConsumer consumer = new AhrszFeedBackConsumer();
        while (generator.hasNext()) {
            consumer.addFeedBack(generator.next());
        }
        translator.serializeToHtml(consumer.ahrsz.hashMapGraph, 100, "src/main/resources/graph.html");
    }
}
