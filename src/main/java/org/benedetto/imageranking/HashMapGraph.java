package org.benedetto.imageranking;

import java.util.*;

public class HashMapGraph {

    private HashMap<Integer, HashMap<Integer, Float>> forward;
    private HashMap<Integer, HashMap<Integer, Float>> backward;

    public HashMapGraph() {
        this.forward = new HashMap<>();
        this.backward = new HashMap<>();
    }

    public Set<Integer> keySet() {
        return this.forward.keySet();
    }

    /* get forward edge */
    public HashMap<Integer, Float> getF(Integer key) {
        return this.forward.get(key);
    }

    /* get backward edge */
    public HashMap<Integer, Float> getB(Integer key) {
        return this.backward.get(key);
    }

    private void addForwardEdge(Integer from, Integer to, Float weight) {
        if (forward.containsKey(from)) {
            if (forward.get(from).containsKey(to)) {
                float original = forward.get(from).get(to);
                forward.get(from).put(to, original + weight);
            } else {
                forward.get(from).put(to, weight);
            }
        } else {
            HashMap outgoing = new HashMap<Integer, Float>();
            outgoing.put(to, weight);
            forward.put(from, outgoing);
        }
    }

    private void addBackwardEdge(Integer from, Integer to, Float weight) {
        if (backward.containsKey(to)) {
            if (backward.get(to).containsKey(from)) {
                float original = backward.get(to).get(from);
                backward.get(to).put(from, original + weight);
            } else {
                backward.get(to).put(from, weight);
            }
        } else {
            HashMap incoming = new HashMap<Integer, Float>();
            incoming.put(from, weight);
            backward.put(to, incoming);
        }
    }

    public void addEdge(Integer from, Integer to, float weight) {
        if (weight < 0f) throw new RuntimeException("Negative weights are not supported.");
        this.addForwardEdge(from, to, weight);
        this.addBackwardEdge(from, to, weight);
    }

    public List<Integer> search(int start, int goal) {
        return this.searchRec(start, goal, new Stack<Integer>());
    }

    public List<Integer> searchRec(int start, int goal, Stack<Integer> path) {
        if (forward.get(start) == null) return null;
        path.push(start);
        // if the start node does not have any outgoing edges, then the current edge cannot be part of a cycle.
        for (Integer newStart : forward.get(start).keySet()) {
            if (newStart.equals(goal)) {
                path.push(newStart);
                return path;
            }
            List<Integer> found = this.searchRec(newStart, goal, path);
            if (found != null) return found;
        }
        path.pop();
        return null;
    }

    public void removeCycle(List<Integer> cycle) {
        int minFrom = cycle.get(cycle.size() - 1);
        int minTo = cycle.get(0);
        float minWeight = forward.get(minFrom).get(minTo);
        for (int i = 0; i < cycle.size() - 1; i++) {
            int from = cycle.get(i);
            int to = cycle.get(i+1);
            float weight = forward.get(from).get(to);
            if (weight < minWeight) {
                minWeight = weight;
            }
        }
        decreaseCycle(cycle, minWeight);
    }

    private void decreaseCycle(List<Integer> cycle, float decrement) {
        Integer firstFrom = cycle.get(cycle.size() - 1);
        Integer firstTo = cycle.get(0);
        float firstEdgeWeight = forward.get(firstFrom).get(firstTo) - decrement;
        if (FloatUtils.floatEqual(firstEdgeWeight, 0f)) {
            forward.get(firstFrom).remove(firstTo);
            backward.get(firstTo).remove(firstFrom);
        } else {
            forward.get(firstFrom).put(firstTo, firstEdgeWeight);
            backward.get(firstTo).put(firstFrom, firstEdgeWeight);
        }
        for (int i = 0; i < cycle.size() - 1; i++) {
            Integer from = cycle.get(i);
            Integer to = cycle.get(i+1);
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
}
