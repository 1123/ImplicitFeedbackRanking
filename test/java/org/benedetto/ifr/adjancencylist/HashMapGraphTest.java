package org.benedetto.ifr.adjancencylist;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class HashMapGraphTest {

    @Test
    public void testTopN() {
        HashMapGraph<Integer> graph = new HashMapGraph<>();
        graph.addEdge(1,2,0.1f);
        graph.addEdge(1,3,0.2f);
        graph.addEdge(2,3,0.1f);
        graph.addEdge(4,2,0.3f);
        List<Edge> result = graph.topNEdges(2);
        Edge<Integer> expected1 = new Edge<>(1,3,0.2f);
        Edge<Integer> expected2 = new Edge<>(4,2,0.3f);
        System.err.println(new Gson().toJson(result));
        assertTrue(result.contains(expected1));
        assertTrue(result.contains(expected2));
    }

}
