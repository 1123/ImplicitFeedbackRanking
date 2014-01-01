package org.benedetto.ifr.util;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class ComparablePair <S extends Comparable<S>,T extends Comparable<T>>
        implements Comparable<ComparablePair<S,T>> {
    public S first;
    public T second;

    public ComparablePair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ComparablePair)) return false;
        @SuppressWarnings("unchecked")
        ComparablePair<S,T> thatPair = (ComparablePair<S,T>) o;
        return (this.compareTo(thatPair) == 0);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(first).append(second).hashCode();
    }

    @Override
    public int compareTo(ComparablePair<S, T> o) {
        if (this.first.compareTo(o.first) != 0) return this.first.compareTo(o.first);
        if (this.second.compareTo(o.second) != 0) return this.second.compareTo(o.second);
        return 0;
    }

}
