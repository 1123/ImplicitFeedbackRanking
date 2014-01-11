package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.FloatUtils;

import java.util.*;

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

public class HashMapGraph<N extends Comparable<N>> {

    public HashMap<N, HashMap<N, Float>> forward;
    public HashMap<N, HashMap<N, Float>> backward;

    public HashMapGraph() {
        this.forward = new HashMap<>();
        this.backward = new HashMap<>();
    }

    public Set<N> nodes() {
        // Using a set as result to eliminate duplicate nodes that are contained both in
        // forward and backward as keys.
        Set<N> result = new TreeSet<>();
        result.addAll(this.forward.keySet());
        result.addAll(this.backward.keySet());
        return result;
    }

    /* get forward edge */
    public HashMap<N, Float> getF(N key) {
        return this.forward.get(key);
    }

    private void addForwardEdge(N from, N to, Float weight) {
        if (forward.containsKey(from)) {
            if (forward.get(from).containsKey(to)) {
                float original = forward.get(from).get(to);
                forward.get(from).put(to, original + weight);
            } else {
                forward.get(from).put(to, weight);
            }
        } else {
            HashMap<N, Float> outgoing = new HashMap<>();
            outgoing.put(to, weight);
            forward.put(from, outgoing);
        }
    }

    private void addBackwardEdge(N from, N to, Float weight) {
        if (backward.containsKey(to)) {
            if (backward.get(to).containsKey(from)) {
                float original = backward.get(to).get(from);
                backward.get(to).put(from, original + weight);
            } else {
                backward.get(to).put(from, weight);
            }
        } else {
            HashMap<N, Float> incoming = new HashMap<>();
            incoming.put(from, weight);
            backward.put(to, incoming);
        }
    }

    public void addEdge(N from, N to, float weight) {
        if (weight < 0f) throw new RuntimeException("Negative weights are not supported.");
        this.addForwardEdge(from, to, weight);
        this.addBackwardEdge(from, to, weight);
    }

    public List<N> search(N start, N goal) {
        return this.searchRec(start, goal, new Stack<N>());
    }

    public List<N> searchRec(N start, N goal, Stack<N> path) {
        if (forward.get(start) == null) return null;
        path.push(start);
        // if the start node does not have any outgoing edges, then the current edge cannot be part of a cycle.
        for (N newStart : forward.get(start).keySet()) {
            if (newStart.equals(goal)) {
                path.push(newStart);
                return path;
            }
            List<N> found = this.searchRec(newStart, goal, path);
            if (found != null) return found;
        }
        path.pop();
        return null;
    }

    public void removeCycle(List<N> cycle) {
        System.err.println("removing cycle: " + cycle);
        N minFrom = cycle.get(cycle.size() - 1);
        N minTo = cycle.get(0);
        float minWeight = forward.get(minFrom).get(minTo);
        for (int i = 0; i < cycle.size() - 1; i++) {
            N from = cycle.get(i);
            N to = cycle.get(i+1);
            float weight = forward.get(from).get(to);
            if (weight < minWeight) {
                minWeight = weight;
            }
        }
        decreaseCycle(cycle, minWeight);
    }

    private void decreaseCycle(List<N> cycle, float decrement) {
        System.err.println("decreasing cycle");
        N firstFrom = cycle.get(cycle.size() - 1);
        N firstTo = cycle.get(0);
        float firstEdgeWeight = forward.get(firstFrom).get(firstTo) - decrement;
        if (FloatUtils.floatEqual(firstEdgeWeight, 0f)) {
            forward.get(firstFrom).remove(firstTo);
            backward.get(firstTo).remove(firstFrom);
        } else {
            forward.get(firstFrom).put(firstTo, firstEdgeWeight);
            backward.get(firstTo).put(firstFrom, firstEdgeWeight);
        }
        for (int i = 0; i < cycle.size() - 1; i++) {
            N from = cycle.get(i);
            N to = cycle.get(i+1);
            float edgeWeight = forward.get(from).get(to) - decrement;
            if (FloatUtils.floatEqual(edgeWeight, 0f)) {
                forward.get(from).remove(to);
                backward.get(to).remove(from);
            } else {
                forward.get(from).put(to, edgeWeight);
                backward.get(to).put(from, edgeWeight);
            }
        }
    }

    public boolean has_edge(N from, N to) {
        if (! this.forward.containsKey(from)) return false;
        if (! this.forward.get(from).containsKey(to)) return false;
        return (! FloatUtils.floatEqual(this.forward.get(from).get(to), 0f));
    }
}
