package org.benedetto.ifr.topologicalsort;

import com.google.common.collect.Lists;
import org.benedetto.ifr.adjancencylist.HashMapGraph;

import java.util.*;

public class AhrszAlgorithm<N extends Comparable<N>> {

    public HashMapGraph<N> hashMapGraph;
    public Map<N,Integer> node2Index;
    private int maxIndex;
    private int minIndex;

    public AhrszAlgorithm() {
        node2Index = new HashMap<>();
        maxIndex = 1;  // the index of the next top insertion
        minIndex = 0;  // the index of the next bottom insertion
        hashMapGraph = new HashMapGraph<>();
    }

    private void put(N node, int index) {
        this.node2Index.put(node, index);
    }

    public void addEdge(N from, N to, float weight) throws InvalidExpansionStateException, InvalidAhrszStateException {
        if (from.equals(to)) return;
        hashMapGraph.addEdge(from, to, weight);
        // both nodes are new.
        if (! node2Index.containsKey(from) && ! node2Index.containsKey(to)) {
            insertTop(from, to);
            return;
        }
        // only <from> is new
        if (! node2Index.containsKey(from)) {
            this.put(from, minIndex);
            this.minIndex--;
            return;
        }
        // only <to> is new
        if (! node2Index.containsKey(to)) {
            this.put(to, maxIndex);
            this.maxIndex++;
            return;
        }
        // neither of the nodes are new, but the topological order need not be changed.
        if (node2Index.get(from) < node2Index.get(to)) return;
        // neither of the nodes are new, and the new edge requires a reordering.
        reorder(from,to);
    }

    private void insertTop(N from, N to) {
        this.put(from, this.maxIndex);
        this.maxIndex++;
        this.put(to, this.maxIndex);
        this.maxIndex++;
    }

    /**
     *
     * @param from source of the new edge
     * @param to sink of the new edge
     */

    private void reorder(final N from, final N to) throws InvalidExpansionStateException {
        ExpansionState<N> es = new ExpansionState<>(from, to);
        while (! es.success) { // repeat until no more cycles found
            // the edge to be inserted may be removed when cycles are detected.
            // Once this newly inserted edge has been removed, there is no need to reorder.
            if (! this.hashMapGraph.hasEdge(from, to)) return;
            es = new ExpansionState<>(from, to);
            this.expand(es);
        }
        // es.check(this.node2Index);
        switchPositions(es.shiftUp, es.shiftDown);
    }

    private void expand(ExpansionState<N> es) throws InvalidExpansionStateException {
        // while the frontiers are not empty.
        while (! es.finished()) {
            es.success = expandForward(es);
            if (! es.success) return;
            es.success = expandBackward(es);
            if (! es.success) return;
        }
    }

    private boolean expandBackward(ExpansionState<N> es) {
        // take the node with the highest priority from the backwardQueue
        // and examine all predecessors.
        if (es.backwardQueue.isEmpty()) { return true; }
        Path<N> highestPath = es.backwardQueue.remove();
        N highest = highestPath.get(highestPath.size() - 1);
        if (this.hashMapGraph.getB(highest) == null) return true;
        for (N predecessor : this.hashMapGraph.getB(highest).keySet()) {
            if (node2Index.get(predecessor) <= node2Index.get(es.to)) continue;
            if (detectAndRemoveCycle(es.forwardQueue, predecessor, highestPath, direction.backward)) return false;
            Path<N> predecessorPath = new Path<N>(highestPath);
            predecessorPath.add(predecessor);
            es.backwardQueue.add(predecessorPath);
            es.shiftDown.add(predecessor);
        }
        return true;
    }

    private boolean expandForward(ExpansionState<N> es) {
        if (es.forwardQueue.isEmpty()) { return true; }
        // take the node with the lowest priority from the forwardQueue,
        // and examine all successors.
        Path<N> lowestPath = es.forwardQueue.remove();
        N lowest = lowestPath.get(lowestPath.size() - 1);
        // check if there are any outgoing edges from the end of the lowest path.
        if (this.hashMapGraph.getF(lowest) == null) return true;
        for (N successor : this.hashMapGraph.getF(lowest).keySet()) {
            if (detectAndRemoveCycle(es.backwardQueue, successor, lowestPath, direction.forward)) return false;
            // only add nodes to the frontier that have higher priority then the source of the
            // new edge.
            if (node2Index.get(successor) >= node2Index.get(es.from)) continue;
            Path<N> successorPath = new Path<N>(lowestPath);
            successorPath.add(successor);
            es.forwardQueue.add(successorPath);
            es.shiftUp.add(successor);
        }
        return true;
    }

    private enum direction { forward, backward }

    private boolean detectAndRemoveCycle(
            PriorityQueue<Path<N>> queue, N successor, Path<N> path, direction dir) {
        if (! queue.contains(new Path<>(successor))) return false;
        ArrayList<Path<N>> backwardList = new ArrayList<>(queue);
        int index = backwardList.indexOf(new Path<>(successor));
        path.addAll(Lists.reverse(backwardList.get(index)));
        if (dir == direction.forward) {
            this.hashMapGraph.removeCycle(path);
        } else {
            this.hashMapGraph.removeCycle(Lists.reverse(path));
        }
        return true;
    }

    public boolean before(N n1, N n2) {
        return this.node2Index.get(n1) < this.node2Index.get(n2);
    }

    private List<N> sortByIndex(Collection<N> toBeSorted) {
        List<N> result = new ArrayList<>(toBeSorted);
        Collections.sort(result, new IndexComparator<N>(this.node2Index));
        return result;
    }

    public void switchPositions(final Set<N> shiftUp, final Set<N> shiftDown) {
        List<N> shiftUpSorted = sortByIndex(shiftUp);
        List<N> shiftDownSorted = sortByIndex(shiftDown);
        List<N> oldOrder = new ArrayList<>(shiftUpSorted);
        oldOrder.addAll(new ArrayList<>(shiftDownSorted));
        Collections.sort(oldOrder, new IndexComparator<>(this.node2Index));
        List<N> newOrder = new ArrayList<>(shiftDownSorted);
        newOrder.addAll(shiftUpSorted);
        HashMap<N, Integer> newNode2Index = new HashMap<>();
        int position = 0;
        for (N n: oldOrder) {
            int index = this.node2Index.get(n);
            newNode2Index.put(newOrder.get(position), index);
            position++;
        }
        for (N elem : newNode2Index.keySet()) {
            this.put(elem, newNode2Index.get(elem));
        }
    }

}

