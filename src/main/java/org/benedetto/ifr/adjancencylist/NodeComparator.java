package org.benedetto.ifr.adjancencylist;

import org.benedetto.ifr.util.Pair;

import java.util.Comparator;

public class NodeComparator implements Comparator<Integer> {

    ClosureGraph cg;

    public NodeComparator(ClosureGraph cg) {
        this.cg = cg;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        if (this.cg.contains(new Pair<>(o1,o2))) return -1;
        if (this.cg.contains(new Pair<>(o2,o1))) return 1;
        return 0;
    }

}
