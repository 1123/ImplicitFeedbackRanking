package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.ComparablePair;
import org.benedetto.ifr.util.Pair;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/31/13
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */

public class ClosureGraph<N extends Comparable<N>> extends ArrayList<ComparablePair<N, N>> {

    AcyclicWeightedGraph<N> g;

    public ClosureGraph() { }

    public ClosureGraph(AcyclicWeightedGraph g) {
        this.g = g;
        this.addAll(initial());
        List<ComparablePair<N, N>> newPairs = this.iterate(initial());
        while (newPairs.size() > 0) {
            this.addAll(newPairs);
            newPairs = this.iterate(newPairs);
        }
    }

    private List<ComparablePair<N, N>> initial() {
        List<ComparablePair<N,N>> result = new ArrayList<>();
        for (N from : g.forward.keySet()) {
            for (N to : g.getF(from).keySet()) {
                result.add(new ComparablePair<>(from,to));
            }
        }
        return result;
    }

    public List<ComparablePair<N, N>> iterate(List<ComparablePair<N, N>> lastDerived) {
        List<ComparablePair<N, N>> newlyDerived = new ArrayList<>();
        // for (x,y) in lastDerived
        for (ComparablePair<N, N> newPair : lastDerived) {
            // for (v,w) in closure
            for (ComparablePair<N, N> oldPair : this) {
                if (newPair.first.equals(oldPair.second)) {
                    ComparablePair<N, N> candidate = new ComparablePair<>(oldPair.first, newPair.second);
                    if (this.contains(candidate) || newlyDerived.contains(candidate)) continue;
                    newlyDerived.add(candidate);
                }
                if (newPair.second.equals(oldPair.first)) {
                    ComparablePair<N, N> candidate = new ComparablePair<>(newPair.first, oldPair.second);
                    if (this.contains(candidate) || newlyDerived.contains(candidate)) continue;
                    newlyDerived.add(candidate);
                }
            }
        }
        return newlyDerived;
    }

    public void sort(List<N> toBeSorted) {
        boolean sorted = false;
        loop: while (! sorted) {
            sorted = true;
            for (ComparablePair<N,N> pair : this) {
                int firstIndex = toBeSorted.indexOf(pair.first);
                int secondIndex = toBeSorted.indexOf(pair.second);
                if (firstIndex > -1 && secondIndex > -1 && firstIndex < secondIndex) { // sort in descending order
                    Collections.swap(toBeSorted, firstIndex, secondIndex);
                    sorted = false;
                    continue loop;
                }
            }
        }
    }

}

