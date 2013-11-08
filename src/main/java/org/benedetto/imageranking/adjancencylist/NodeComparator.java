package org.benedetto.imageranking.adjancencylist;

import org.benedetto.imageranking.util.Pair;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

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

    @Override
    public Comparator<Integer> reverseOrder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comparator<Integer> thenComparing(Comparator<? super Integer> other) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<Integer> thenComparing(Function<? super Integer, ? extends U> keyExtractor) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comparator<Integer> thenComparing(ToIntFunction<? super Integer> keyExtractor) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comparator<Integer> thenComparing(ToLongFunction<? super Integer> keyExtractor) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comparator<Integer> thenComparing(ToDoubleFunction<? super Integer> keyExtractor) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
