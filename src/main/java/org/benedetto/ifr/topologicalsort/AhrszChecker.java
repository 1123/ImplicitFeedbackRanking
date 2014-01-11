package org.benedetto.ifr.topologicalsort;

public class AhrszChecker {

    public static <N extends Comparable<N>> void checkAhrsz(final Ahrsz<N> ahrsz)
            throws InvalidAhrszStateException {
        checkForward(ahrsz);
        checkBackward(ahrsz);
    }

    private static <N extends Comparable<N>> void checkForward(final Ahrsz<N> ahrsz)
            throws InvalidAhrszStateException {
        for (final N source : ahrsz.hashMapGraph.forward.keySet()) {
            for (final N sink : ahrsz.hashMapGraph.forward.get(source).keySet()) {
                if (ahrsz.node2Index.get(source) >= ahrsz.node2Index.get(sink))
                    throw new InvalidAhrszStateException();
            }
        }

    }

    private static <N extends Comparable<N>> void checkBackward(final Ahrsz<N> ahrsz)
            throws InvalidAhrszStateException {
        for (final N source : ahrsz.hashMapGraph.backward.keySet()) {
            for (final N sink : ahrsz.hashMapGraph.backward.get(source).keySet()) {
                if (ahrsz.node2Index.get(source) <= ahrsz.node2Index.get(sink))
                    throw new InvalidAhrszStateException();
            }
        }
    }

}
