package org.benedetto.ifr.topologicalsort;

import org.junit.Test;

import java.util.*;

class MyComparator<N extends Comparable<N>> implements Comparator<N> {

    private final Map<N, Integer> node2index;

    public MyComparator(Map<N, Integer> node2Index) {
        this.node2index = node2Index;
    }

    @Override
    public int compare(N o1, N o2) {
        if (! node2index.containsKey(o1)) return -1;
        if (! node2index.containsKey(o2)) return 1;
        int result = Integer.compare(node2index.get(o1), node2index.get(o2));
        return result;
    }
}

public class IndexComparatorTest {

    @Test
    public void testSort() {
        Vector<Integer> toBeSorted = new Vector<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31));
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, 5);
        hashMap.put(14, 6);
        hashMap.put(15, -2);
        hashMap.put(16, 4);
        hashMap.put(17, 1);
        hashMap.put(18, 8);
        hashMap.put(19, -12);
        hashMap.put(21, -3);
        hashMap.put(22, -13);
        hashMap.put(24, -19);
        hashMap.put(25, 2);
        hashMap.put(27, 7);
        hashMap.put(28, -6);
        hashMap.put(31, 0);
        hashMap.put(30, -4);
        Collections.sort(toBeSorted, new MyComparator<>(hashMap));
    }

}
