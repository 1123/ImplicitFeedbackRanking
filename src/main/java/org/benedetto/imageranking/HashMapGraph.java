package org.benedetto.imageranking;

import java.util.*;

public class HashMapGraph extends HashMap<Integer, HashMap<Integer, Float>> {

    public void addEdge(Integer from, Integer to, float weight) {
        if (weight < 0f) throw new RuntimeException("Negative weights are not supported.");
        if (this.containsKey(from)) {
            if (this.get(from).containsKey(to)) {
                float original = this.get(from).get(to);
                this.get(from).put(to, original + weight);
            } else {
                this.get(from).put(to, weight);
            }
        } else {
            HashMap outgoing = new HashMap<Integer, Float>();
            outgoing.put(to, weight);
            this.put(from, outgoing);
        }
    }

    public List<Integer> search(int start, int goal) {
        return this.searchRec(start, goal, new Stack<Integer>());
    }

    public List<Integer> searchRec(int start, int goal, Stack<Integer> path) {
        path.push(start);
        // if the start node does not have any outgoing edges, then the current edge cannot be part of a cycle.
        if (this.get(start) == null) return null;
        for (Integer newStart : this.get(start).keySet()) {
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
        float minWeight = this.get(minFrom).get(minTo);
        for (int i = 0; i < cycle.size() - 1; i++) {
            int from = cycle.get(i);
            int to = cycle.get(i+1);
            float weight = this.get(from).get(to);
            if (weight < minWeight) {
                minWeight = weight;
            }
        }
        decreaseCycle(cycle, minWeight);
    }

    private void decreaseCycle(List<Integer> cycle, float decrement) {
        int firstFrom = cycle.get(cycle.size() - 1);
        int firstTo = cycle.get(0);
        float firstEdgeWeight = this.get(firstFrom).get(firstTo) - decrement;
        if (FloatUtils.floatEqual(firstEdgeWeight, 0f)) {
            this.get(firstFrom).remove(cycle.get(firstTo));
        }
        this.get(cycle.get(cycle.size() - 1)).put(cycle.get(0), firstEdgeWeight);
        for (int i = 0; i < cycle.size() - 1; i++) {
            int from = cycle.get(i);
            int to = cycle.get(i+1);
            float edgeWeight = this.get(from).get(to) - decrement;
            if (FloatUtils.floatEqual(edgeWeight, 0f)) {
                this.get(from).remove(to);
            } else {
                this.get(from).put(to, edgeWeight);
            }
        }
    }
}
