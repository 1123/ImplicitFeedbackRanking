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
        if (! node2index.containsKey(o1) && node2index.containsKey(o2)) return -1;
        if (! node2index.containsKey(o2) && node2index.containsKey(o1)) return 1;
        if (! node2index.containsKey(o1) && ! node2index.containsKey(o2)) return 0;
        int result = Double.compare(node2index.get(o1), node2index.get(o2));
        return result;
    }
}
