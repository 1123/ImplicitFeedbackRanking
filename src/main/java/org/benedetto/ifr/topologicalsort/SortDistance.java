package org.benedetto.ifr.topologicalsort;

import java.util.List;

public class SortDistance {

    public static <N> int distance(List<N> correct, List<N> estimated) {
        int distance = 0;
        for (N element : correct) {
            int correctPosition = correct.indexOf(element);
            int estimatedPosition = estimated.indexOf(element);
            distance += Math.abs(correctPosition - estimatedPosition);
        }
        return distance;
    }

}
