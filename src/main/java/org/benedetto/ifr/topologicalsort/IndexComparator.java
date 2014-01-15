package org.benedetto.ifr.topologicalsort;

import java.util.Comparator;
import java.util.Map;

public class IndexComparator<N extends Comparable<N>> implements Comparator<N> {

    private final Map<N, Integer> node2index;

    public IndexComparator(Map<N, Integer> node2Index) {
        this.node2index = node2Index;
    }

    @Override
    public int compare(N o1, N o2) {
        if (node2index.get(o1) == null) return 0;
        if (node2index.get(o2) == null) return 0;
        return node2index.get(o1).compareTo(node2index.get(o2));
    }
}
