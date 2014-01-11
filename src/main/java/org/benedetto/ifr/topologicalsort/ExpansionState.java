package org.benedetto.ifr.topologicalsort;

import java.util.*;

public class ExpansionState <N extends Comparable<N>> {
    PriorityQueue<Path<N>> forwardQueue;
    PriorityQueue<Path<N>> backwardQueue;
    N from;
    N to;
    Set<N> shiftUp;
    Set<N> shiftDown;
    boolean success;

    public ExpansionState(N from, N to) {
        forwardQueue = new PriorityQueue<>();
        backwardQueue = new PriorityQueue<>(11, Collections.reverseOrder());
        this.from = from;
        this.to = to;
        shiftUp = new TreeSet<>();
        shiftDown = new TreeSet<>();
        shiftDown.add(from);
        shiftUp.add(to);
        success = false;
        Path<N> fromPath = new Path<>(from);
        this.backwardQueue.add(fromPath);
        Path<N> toPath = new Path<>(to);
        this.forwardQueue.add(toPath);
    }

    public boolean finished() {
        return forwardQueue.isEmpty() && backwardQueue.isEmpty();
    }

    public void check(Map<N,Integer> node2index) throws InvalidExpansionStateException {
        for (Path<N> forwardPath : this.forwardQueue) {
            if (! forwardPath.ascending(node2index)) {
                System.err.println(forwardPath);
                throw new InvalidExpansionStateException();
            }
        }
        for (Path<N> backwardPath : this.backwardQueue) {
            if (! backwardPath.descending(node2index)) {
                System.err.println(backwardPath);
                throw new InvalidExpansionStateException();
            }
        }
    }

}

