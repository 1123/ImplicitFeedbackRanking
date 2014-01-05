package org.benedetto.ifr.topologicalsort;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AHRSZTest {

    @Test
    public void testSingleEdge() throws CycleException {
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(1,2,0.1f);
        assert(ahrsz.before(1,2));
    }

    /**
     * A->B   C->D
     * ^------+
     * @throws CycleException
     */

    @Test
    public void testReorder() throws CycleException {
        Ahrsz<Character> ahrsz = new Ahrsz<>();
        ahrsz.addEdge('A','B',0.1f);
        ahrsz.addEdge('C','D',0.1f);
        ahrsz.addEdge('C','A',0.1f);
        assertTrue(ahrsz.before('A','B'));
        assertTrue(ahrsz.before('C','D'));
        assertTrue(ahrsz.before('C','A'));
    }

    @Test(expected=CycleException.class)
    public void testSingleNodeCycle() throws CycleException {
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(1,1,0.1f);
    }

    @Test
    public void testComplexGraph() throws CycleException {
        Ahrsz<Character> ahrsz = new Ahrsz<>();
        ahrsz.addEdge('A','B',0.1f);
        ahrsz.addEdge('C','E',0.1f);
        ahrsz.addEdge('E','F',0.1f);
        ahrsz.addEdge('G','F',0.1f);
        assertTrue(ahrsz.before('A','B'));
        assertTrue(ahrsz.before('C','E'));
        assertTrue(ahrsz.before('E','F'));
        assertTrue(ahrsz.before('G','F'));
        assertTrue(ahrsz.before('G','A'));
        ahrsz.addEdge('E','A',0.1f);
        assertTrue(ahrsz.before('G','C'));
        assertTrue(ahrsz.before('C','E'));
        assertTrue(ahrsz.before('E','A'));
        assertTrue(ahrsz.before('A','B'));
        assertTrue(ahrsz.before('B','F'));
    }

    @Test(expected=CycleException.class)
    public void testMultipleNodeCycle() throws CycleException {
        Ahrsz<Integer> ahrsz = new Ahrsz<>();
        ahrsz.addEdge(1,2,0.1f);
        ahrsz.addEdge(2,3,0.1f);
        ahrsz.addEdge(3,4,0.1f);
        ahrsz.addEdge(4,1,0.1f);
    }


}
