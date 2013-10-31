package org.benedetto.imageranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/17/13
 * Time: 23:31
 * This class is used to remove cycles from a directed weighted graph, substracting the lowest of all weights from all edges within a cycle.
 * Problem: the result is non-deterministic if an edge is part of more than one cycle.
 */

public class CycleRemover {

    AdjacencyGraph dwg;

    public CycleRemover(AdjacencyGraph dwg) {
        this.dwg = dwg;
    }

    public void search() {
        List<Integer> visited = new ArrayList<>();
        for (int start = 0; start < this.dwg.nodes; start++) {
            if (visited.contains(new Integer(start))) continue;
            expand(start, visited);
        }
    }

    private void expand(int start, List<Integer> visited) {
        Stack<Integer> path = new Stack<>();
        path.push(start);
        for (int out : this.dwg.outgoing(start)) {
            if (this.expandRec(out, visited, path)) {
                expand(out, visited);
                return;
            }
            visited.add(start);
        }
        path.pop();
    }

    private boolean expandRec(int current, List<Integer> visited, Stack<Integer> path) {
        if (path.contains(current)) {
            this.removeCycle(current, path);
            return true;
        } else {
            path.push(current);
            for (int out : this.dwg.outgoing(current)) {
                if (visited.contains(out)) continue;
                if (expandRec(out, visited, path)) return true;
            }
            visited.add(current);
            path.pop();
            return false;
        }
    }

    private void removeCycle(int last, Stack<Integer> path) {
        path.push(last);
        List<Integer> pathAsList = new ArrayList<>(path);
        List<Float> weights = new ArrayList<>();
        for (int i = 0; i < pathAsList.size() -1; i++) {
            weights.add(this.dwg.get(pathAsList.get(i),pathAsList.get(i+1)));
        }
        float minimum = this.minimum(weights);
        while(path.size() > 1) {
            int to = path.pop();
            int from = path.peek();
            this.dwg.addEdge(from, to, -minimum);
        }
        path.pop();
    }

    private float minimum(List<Float> in) {
        float result = Float.MAX_VALUE;
        for (float member : in) {
            result = Math.min(result, member);
        }
        return result;
    }

}
