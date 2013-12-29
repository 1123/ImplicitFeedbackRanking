package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.ComparablePair;
import org.benedetto.ifr.util.Pair;

import java.util.Comparator;

public class NodeComparator <N extends Comparable<N>> implements Comparator<N> {

    ClosureGraph<N> cg;

    public NodeComparator(ClosureGraph<N> cg) {
        this.cg = cg;
    }

    @Override
    public int compare(N o1, N o2) {
        if (this.cg.contains(new ComparablePair<>(o1,o2))) return -1;
        if (this.cg.contains(new ComparablePair<>(o2,o1))) return 1;
        return 0;
    }

}
