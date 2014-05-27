package org.benedetto.ifr.adjancencylist;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class HashMapGraphTest {

    @Test
    public void testTopN() {
        HashMapGraph<Integer> graph = new HashMapGraph<>();
        graph.addEdge(1,2,0.1f);
        graph.addEdge(1,3,0.2f);
        graph.addEdge(2,3,0.1f);
        graph.addEdge(4,2,0.3f);
        List<Edge<Integer>> result = graph.topNEdges(2);
        Edge<Integer> expected1 = new Edge<>(1,3,0.2f);
        Edge<Integer> expected2 = new Edge<>(4,2,0.3f);
        System.err.println(new Gson().toJson(result));
        assertTrue(result.contains(expected1));
        assertTrue(result.contains(expected2));
    }

}
