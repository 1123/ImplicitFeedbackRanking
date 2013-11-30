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

public class ClosureGraph extends ArrayList<Pair<Integer, Integer>> {

    AcyclicWeightedGraph g;

    public ClosureGraph(AcyclicWeightedGraph g) {
        this.g = g;
        this.addAll(initial());
        List<Pair<Integer, Integer>> newPairs = this.iterate(initial());
        while (newPairs.size() > 0) {
            this.addAll(newPairs);
            newPairs = this.iterate(newPairs);
        }
    }

    private List<Pair<Integer, Integer>> initial() {
        List<Pair<Integer,Integer>> result = new ArrayList<Pair<Integer,Integer>>();
        for (Integer from : g.keySet()) {
            for (Integer to : g.getF(from).keySet()) {
                result.add(new Pair<>(from,to));
            }
        }
        return result;
    }

    public List<Pair<Integer, Integer>> iterate(List<Pair<Integer, Integer>> lastDerived) {
        List<Pair<Integer, Integer>> newlyDerived = new ArrayList<>();
        // for (x,y) in lastDerived
        for (Pair<Integer, Integer> newPair : lastDerived) {
            // for (v,w) in closure
            for (Pair<Integer, Integer> oldPair : this) {
                if (newPair.first.equals(oldPair.second)) {
                    Pair<Integer, Integer> candidate = new Pair<>(oldPair.first, newPair.second);
                    if (this.contains(candidate) || newlyDerived.contains(candidate)) continue;
                    newlyDerived.add(candidate);
                }
                if (newPair.second.equals(oldPair.first)) {
                    Pair<Integer, Integer> candidate = new Pair<>(newPair.first, oldPair.second);
                    if (this.contains(candidate) || newlyDerived.contains(candidate)) continue;
                    newlyDerived.add(candidate);
                }
            }
        }
        return newlyDerived;
    }

}

