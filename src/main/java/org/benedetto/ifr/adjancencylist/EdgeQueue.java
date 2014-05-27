package org.benedetto.ifr.adjancencylist;

import org.apache.lucene.util.PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class EdgeQueue <N> extends PriorityQueue<Edge<N>> {

    public EdgeQueue(int maxSize) {
        super(maxSize);
    }

    @Override
    protected boolean lessThan(Edge<N> edge, Edge<N> edge2) {
        return edge.weight.compareTo(edge2.weight) < 0;
    }

    public List<Edge<N>> asList() {
        ArrayList<Edge<N>> result = new ArrayList<>();
        while ( this.size() > 0 ) {
            result.add(this.pop());
        }
        return result;
    }

}
