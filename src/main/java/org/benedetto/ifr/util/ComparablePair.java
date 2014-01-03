package org.benedetto.ifr.util;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

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
        return Objects.hashCode(this.first, this.second);
    }

    @Override
    public int compareTo(ComparablePair<S, T> o) {
        return ComparisonChain.start()
                .compare(this.first, o.first)
                .compare(this.second, o.second)
                .result();
    }

}
