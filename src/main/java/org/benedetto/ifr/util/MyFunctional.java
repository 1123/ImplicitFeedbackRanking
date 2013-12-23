package org.benedetto.ifr.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 12/23/13
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class MyFunctional {

    public static <A,B> List<Pair<A,B>> zip(Collection<A> as, Collection<B> bs) {
        List<Pair<A,B>> result = new ArrayList<>();
        int minSize = Math.min(as.size(), bs.size());
        Iterator<A> asIterator = as.iterator();
        Iterator<B> bsIterator = bs.iterator();
        for (int i = 0; i < minSize; i++) {
            result.add(new Pair<>(asIterator.next(), bsIterator.next()));
        }
        return result;
    }

}
