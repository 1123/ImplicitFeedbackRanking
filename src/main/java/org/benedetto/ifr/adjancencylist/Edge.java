package org.benedetto.ifr.adjancencylist;

/**
 * This class maintains an acyclic weighted directed graph upon insertions of edges.
 * Upon insertion of edges, it is checked whether the introduction of the edge would
 * complete a cycle. In this case the cycle is decreased by the minimum edge weight
 * of the edges composing the cycle. This way the edge with the minimal weight is
 * removed. Note that the insertion of an edge may cause multiple cycles to be completed.
 * In this case the first cycle found in removed. A smarter choice could be to
 * split the newly inserted edge into multiple virtual edges, one for each newly introduced
 * cycle.
 *
 * @param <N>: The datatype of the Nodes. Might be Integers, Strings, Urls, etc.
 */

public class Edge<N> implements Comparable<Edge<N>> {

    Float weight;
    N from;
    N to;

    public Edge(N from, N to, Float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge<N> o) {
        return this.weight.compareTo(o.weight);
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Edge)) return false;
        Edge otherEdge = (Edge<N>) other;
        return
            this.from.equals(otherEdge.from)
            && (this.to.equals(otherEdge.to))
            && (this.weight.equals(otherEdge.weight));
    }
}
