package org.benedetto.ifr.topologicalsort;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Path<N extends Comparable> extends ArrayList<N> implements Comparable<Path<N>> {

    public Path() {
        super();
    }

    public Path(N singleElement) {
        this();
        this.add(singleElement);
    }

    public Path(List<N> list) {
        super(list);
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) return false;
        @SuppressWarnings("unchecked")
        Path<N> o = (Path<N>) other;
        return this.compareTo(o) == 0;
    }

    @Override
    public int compareTo(Path<N> other) {
        N thisLast = this.get(this.size() - 1);
        N otherLast = other.get(other.size() - 1);
        // possible BUG: elements within the priority queue should be compared using node2index.
        return thisLast.compareTo(otherLast);
    }

    boolean ascending(Map<N, Integer> node2index) {
        int max = node2index.get(this.get(0));
        for (N elem : this) {
            if (node2index.get(elem) < max) return false;
        }
        return true;
    }

    boolean descending(Map<N, Integer> node2index) {
        Path reverse = new Path(Lists.reverse(this));
        return reverse.ascending(node2index);
    }

}
