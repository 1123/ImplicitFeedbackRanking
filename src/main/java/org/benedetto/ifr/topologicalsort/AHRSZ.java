package org.benedetto.ifr.topologicalsort;

import com.google.common.collect.Lists;
import org.benedetto.ifr.adjancencylist.HashMapGraph;

import java.util.*;

public class Ahrsz<N extends Comparable<N>> {

    private HashMapGraph<N> hashMapGraph;
    private Map<N,Integer> node2Index;
    private Map<Integer, N> index2Node;
    private int maxIndex;
    private int minIndex;

    public Ahrsz() {
        node2Index = new HashMap<>();
        index2Node = new HashMap<>();
        maxIndex = 1;  // the index of the next top insertion
        minIndex = 0;  // the index of the next bottom insertion
        hashMapGraph = new HashMapGraph<>();
    }

    private void put(N node, int index) {
        this.node2Index.put(node, index);
        this.index2Node.put(index, node);
    }

    public void addEdge(N from, N to, float weight) throws CycleException {
        if (from.equals(to)) throw new CycleException();
        // both nodes are new.
        if (! node2Index.containsKey(from) && ! node2Index.containsKey(to)) {
            insertTop(from, to, weight);
            return;
        }
        // only from is new
        if (! node2Index.containsKey(from)) {
            this.put(from, minIndex);
            this.minIndex--;
            hashMapGraph.addEdge(from, to, weight);
            return;
        }
        // only to is new
        if (! node2Index.containsKey(to)) {
            this.put(to, maxIndex);
            this.maxIndex++;
            hashMapGraph.addEdge(from, to, weight);
            return;
        }
        // neither of the nodes are new, but the topological order need not be changed.
        if (node2Index.get(from) < node2Index.get(to)) {
            hashMapGraph.addEdge(from, to, weight);
            return;
        }
        // neither of the nodes are new, and the new edge requires a reordering.
        reorder(from,to,weight);
    }

    private void insertTop(N from, N to, float weight) {
        this.put(from, this.maxIndex);
        this.maxIndex++;
        this.put(to, this.maxIndex);
        this.maxIndex++;
        hashMapGraph.addEdge(from, to, weight);
    }

    private void reorder(final N from, final N to, final float weight) throws CycleException {
        PriorityQueue<Path> backwardQueue = new PriorityQueue<>();
        Path fromPath = new Path();
        fromPath.add(from);
        backwardQueue.add(fromPath);
        PriorityQueue<Path> forwardQueue =
                new PriorityQueue<>(11, Collections.reverseOrder());
        Path toPath = new Path();
        toPath.add(to);
        forwardQueue.add(toPath);
        List<N> shiftUp = new ArrayList<>();
        shiftUp.add(to);
        List<N> shiftDown = new ArrayList<>();
        shiftDown.add(from);
        expand(forwardQueue, backwardQueue, from, to, shiftUp, shiftDown);
        switchPositions(shiftUp, shiftDown);
    }

    private void expand(
            PriorityQueue<Path> forwardQueue,
            PriorityQueue<Path> backwardQueue,
            final N from, final N to,
            List<N> shiftUp,
            List<N> shiftDown
    ) throws CycleException {
        expandForward(forwardQueue, from, shiftDown, shiftUp);
        expandBackward(backwardQueue, to, shiftDown, shiftUp);
        if ((! forwardQueue.isEmpty()) || (! backwardQueue.isEmpty())) {
            expand(forwardQueue, backwardQueue, from, to, shiftUp, shiftDown);
        }
    }

    private void expandBackward(
            PriorityQueue<Path> backwardQueue, final N to,
            List<N> shiftDown, List<N> shiftUp) throws CycleException {
        // take the node with the highest priority from the backwardQueue
        // and examine all predecessors.
        if (backwardQueue.isEmpty()) return;
        Path highestPath = backwardQueue.remove();
        N highest = highestPath.get(highestPath.size() - 1);
        if (this.hashMapGraph.backward.get(highest) == null) return;
        for (N predecessor : this.hashMapGraph.backward.get(highest).keySet()) {
            if (node2Index.get(predecessor) > node2Index.get(to)) {
                if (shiftUp.contains(predecessor)) throw new CycleException();
                Path predecessorPath = new Path(highestPath);
                predecessorPath.add(predecessor);
                backwardQueue.add(predecessorPath);
                shiftDown.add(predecessor);
            }
        }
    }

    private void expandForward(
            PriorityQueue<Path> forwardQueue, final N from,
            List<N> shiftDown, List<N> shiftUp) throws CycleException {
        if (forwardQueue.isEmpty()) return;
        // take the node with the lowest priority from the forwardQueue,
        // and examine all successors.
        Path lowestPath = forwardQueue.remove();
        N lowest = lowestPath.get(lowestPath.size() - 1);
        if (this.hashMapGraph.forward.get(lowest) == null) return;
        for (N successor : this.hashMapGraph.forward.get(lowest).keySet()) {
            if (node2Index.get(successor) < node2Index.get(from)) {
                if (shiftDown.contains(successor)) throw new CycleException();
                Path successorPath = new Path(lowestPath);
                successorPath.add(successor);
                forwardQueue.add(successorPath);
                shiftUp.add(successor);
            }
        }
    }

    public boolean before(N n1, N n2) {
        return this.node2Index.get(n1) < this.node2Index.get(n2);
    }

    private void switchPositions(List<N> shiftUp, List<N> shiftDown) {
        List<N> oldOrder = new ArrayList<>();
        oldOrder.addAll(shiftUp);
        oldOrder.addAll(Lists.reverse(shiftDown));
        List<N> newOrder = new ArrayList<>();
        newOrder.addAll(Lists.reverse(shiftDown));
        newOrder.addAll(shiftUp);
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

    class Path extends ArrayList<N> implements Comparable<Path> {

        public Path() {
            super();
        }

        public Path(List<N> list) {
            super(list);
        }

        @Override
        public int compareTo(Path other) {
            N thisLast = this.get(this.size());
            N otherLast = other.get(other.size());
            return thisLast.compareTo(otherLast);
        }
    }

}
