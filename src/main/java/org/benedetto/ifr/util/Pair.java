package org.benedetto.ifr.util;

public class Pair <S,T> {
    public S first;
    public T second;

    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        Pair<S,T> thatPair = (Pair<S,T>) o;
        return (this.first.equals(thatPair.first) && this.second.equals(thatPair.second));
    }
}
