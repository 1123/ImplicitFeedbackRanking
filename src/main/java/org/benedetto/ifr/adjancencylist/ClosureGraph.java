package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.Pair;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/31/13
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */

public class ClosureGraph<N> extends ArrayList<Pair<N, N>> {

    AcyclicWeightedGraph<N> g;

    public ClosureGraph(AcyclicWeightedGraph g) {
        this.g = g;
        this.addAll(initial());
        List<Pair<N, N>> newPairs = this.iterate(initial());
        while (newPairs.size() > 0) {
            this.addAll(newPairs);
            newPairs = this.iterate(newPairs);
        }
    }

    private List<Pair<N, N>> initial() {
        List<Pair<N,N>> result = new ArrayList<Pair<N,N>>();
        for (N from : g.keySet()) {
            for (N to : g.getF(from).keySet()) {
                result.add(new Pair<>(from,to));
            }
        }
        return result;
    }

    public List<Pair<N, N>> iterate(List<Pair<N, N>> lastDerived) {
        List<Pair<N, N>> newlyDerived = new ArrayList<>();
        // for (x,y) in lastDerived
        for (Pair<N, N> newPair : lastDerived) {
            // for (v,w) in closure
            for (Pair<N, N> oldPair : this) {
                if (newPair.first.equals(oldPair.second)) {
                    Pair<N, N> candidate = new Pair<>(oldPair.first, newPair.second);
                    if (this.contains(candidate) || newlyDerived.contains(candidate)) continue;
                    newlyDerived.add(candidate);
                }
                if (newPair.second.equals(oldPair.first)) {
                    Pair<N, N> candidate = new Pair<>(newPair.first, oldPair.second);
                    if (this.contains(candidate) || newlyDerived.contains(candidate)) continue;
                    newlyDerived.add(candidate);
                }
            }
        }
        return newlyDerived;
    }

}

