package org.benedetto.ifr.adjancencylist;

import org.apache.lucene.util.PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class EdgeQueue extends PriorityQueue<Edge> {

    public EdgeQueue(int maxSize) {
        super(maxSize);
    }

    @Override
    protected boolean lessThan(Edge edge, Edge edge2) {
        return edge.weight.compareTo(edge2.weight) < 0;
    }

    public List<Edge> asList() {
        ArrayList<Edge> result = new ArrayList<>();
        while ( this.size() > 0 ) {
            result.add(this.pop());
        }
        return result;
    }

}
