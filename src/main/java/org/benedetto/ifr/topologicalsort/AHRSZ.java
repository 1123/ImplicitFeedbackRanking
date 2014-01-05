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
        hashMapGraph.addEdge(from, to, weight);
        // both nodes are new.
        if (! node2Index.containsKey(from) && ! node2Index.containsKey(to)) {
            insertTop(from, to);
            return;
        }
        // only from is new
        if (! node2Index.containsKey(from)) {
            this.put(from, minIndex);
            this.minIndex--;
            return;
        }
        // only to is new
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
     * @throws CycleException
     */

    private void reorder(final N from, final N to) throws CycleException {
        boolean expanded = false;
        List<N> shiftUp = null;
        List<N> shiftDown = null;
        while (!expanded) {
            PriorityQueue<Path<N>> backwardQueue = new PriorityQueue<>();
            Path<N> fromPath = new Path<>(from);
            backwardQueue.add(fromPath);
            PriorityQueue<Path<N>> forwardQueue =
                    new PriorityQueue<>(11, Collections.reverseOrder());
            Path<N> toPath = new Path<>(to);
            forwardQueue.add(toPath);
            shiftUp = new ArrayList<>();
            shiftUp.add(to);
            shiftDown = new ArrayList<>();
            shiftDown.add(from);
            expanded = expand(forwardQueue, backwardQueue, from, to, shiftUp, shiftDown);
        }
        switchPositions(shiftUp, shiftDown);
    }

    private boolean expand(
            PriorityQueue<Path<N>> forwardQueue,
            PriorityQueue<Path<N>> backwardQueue,
            final N from, final N to,
            List<N> shiftUp,
            List<N> shiftDown
    ) throws CycleException {
        if (! expandForward(forwardQueue, backwardQueue, from, shiftUp)) return false;
        if (! expandBackward(forwardQueue, backwardQueue, to, shiftDown)) return false;
        if ((! forwardQueue.isEmpty()) || (! backwardQueue.isEmpty())) {
            if (! expand(forwardQueue, backwardQueue, from, to, shiftUp, shiftDown)) return false;
        }
        return true;
    }

    private boolean expandBackward(PriorityQueue<Path<N>> forwardQueue,
            PriorityQueue<Path<N>> backwardQueue, final N to,
            List<N> shiftDown) throws CycleException {
        // take the node with the highest priority from the backwardQueue
        // and examine all predecessors.
        if (backwardQueue.isEmpty()) return true;
        Path<N> highestPath = backwardQueue.remove();
        N highest = highestPath.get(highestPath.size() - 1);
        if (this.hashMapGraph.backward.get(highest) == null) return true;
        for (N predecessor : this.hashMapGraph.backward.get(highest).keySet()) {
            if (node2Index.get(predecessor) <= node2Index.get(to)) continue;
            if (detectAndRemoveCycle(forwardQueue, predecessor, highestPath)) return false;
            Path<N> predecessorPath = new Path<N>(highestPath);
            predecessorPath.add(predecessor);
            backwardQueue.add(predecessorPath);
            shiftDown.add(predecessor);
        }
        return true;
    }

    /**
     * This method looks for nodes that must be assigned a new priority in forward direction,
     * starting at the end/sink of the newly inserted edge. This method is recursive.
     *
     * @param from is the source of the newly inserted edge.
     *             This is an upper bound for all nodes that may need to be assigned a new priority.
     * @param forwardQueue is the priority queue containing all paths which must be further
     *                     expanded in the search for nodes to be prioritized. This method takes one
     *                     element from the forwardQueue and may add one ore more new paths to it.
     * @param backwardQueue contains all paths currently expanded in backward direction. This+
     *                      parameter is not manipulated, but only queried for detecting cycles.
     * @param shiftUp contains all nodes whose priority must be increased. This method may
     *                add additional nodes to this list.
     *
     * @return false if and only if a cycle has been detected and removed. True if no cycle has
     * been found. In this case shiftUp contains the nodes whose priority
     * must be increased, shiftDown contains the nodes whose priorities must be decreased.
     *
     **/

    private boolean expandForward(PriorityQueue<Path<N>> forwardQueue,
            final PriorityQueue<Path<N>> backwardQueue, final N from,
            List<N> shiftUp) throws CycleException {
        if (forwardQueue.isEmpty()) return true;
        // take the node with the lowest priority from the forwardQueue,
        // and examine all successors.
        Path<N> lowestPath = forwardQueue.remove();
        N lowest = lowestPath.get(lowestPath.size() - 1);
        if (this.hashMapGraph.forward.get(lowest) == null) return true;
        for (N successor : this.hashMapGraph.forward.get(lowest).keySet()) {
            // only add nodes to the frontier that have higher priority then the source of the
            // new edge.
            if (node2Index.get(successor) >= node2Index.get(from)) continue;
            if (detectAndRemoveCycle(backwardQueue, successor, lowestPath)) return false;
            Path<N> successorPath = new Path<N>(lowestPath);
            successorPath.add(successor);
            forwardQueue.add(successorPath);
            shiftUp.add(successor);
        }
        return true;
    }

    private boolean detectAndRemoveCycle(
            PriorityQueue<Path<N>> queue, N successor, Path<N> path) {
        if (! queue.contains(new Path<>(successor))) return false;
        ArrayList<Path<N>> backwardList = new ArrayList<>(queue);
        int index = backwardList.indexOf(new Path<>(successor));
        path.addAll(Lists.reverse(backwardList.get(index)));
        // TODO: we may need to add the edge from->to to the cycle.
        this.hashMapGraph.removeCycle(path);
        return true;
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

}

