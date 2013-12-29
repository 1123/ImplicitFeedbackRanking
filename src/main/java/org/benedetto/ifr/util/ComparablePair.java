package org.benedetto.ifr.util;

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
        ComparablePair<S,T> thatPair = (ComparablePair<S,T>) o;
        return (this.compareTo(thatPair) == 0);
    }

    @Override
    public int compareTo(ComparablePair<S, T> o) {
        if (this.first.compareTo(o.first) != 0) return this.first.compareTo(o.first);
        if (this.second.compareTo(o.second) != 0) return this.second.compareTo(o.second);
        return 0;
    }
}
