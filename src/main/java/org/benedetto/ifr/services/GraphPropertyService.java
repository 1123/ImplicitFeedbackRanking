package org.benedetto.ifr.services;

import org.benedetto.ifr.adjancencylist.Edge;
import org.benedetto.ifr.adjancencylist.EdgeQueue;
import org.benedetto.ifr.adjancencylist.HashMapGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GraphPropertyService {

    public <N extends Comparable<N>> List<Edge<N>> topNEdges(HashMapGraph<N> graph, int n) {
        EdgeQueue<N> result = new EdgeQueue<>(n);
        for (N from : graph.forward.keySet()) {
            for (N to: graph.forward.get(from).keySet()) {
                result.insertWithOverflow(new Edge<>(from, to, graph.forward.get(from).get(to)));
            }
        }
        return new ArrayList<>(result.asList());
    }

    public <N extends Comparable<N>> List<N> topNEdgeNodes(HashMapGraph<N> graph, int n) {
        List<Edge<N>> edges = this.topNEdges(graph, n);
        Set<N> result = new TreeSet<>();
        for (Edge<N> e : edges) {
            result.add(e.getFrom());
            result.add(e.getTo());
        }
        return new ArrayList<>(result);
    }

}
