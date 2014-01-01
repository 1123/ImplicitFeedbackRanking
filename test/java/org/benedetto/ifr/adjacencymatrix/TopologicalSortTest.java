package org.benedetto.ifr.adjacencymatrix;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.benedetto.ifr.adjancencylist.AcyclicWeightedGraph;
import org.benedetto.ifr.adjancencylist.ClosureGraph;
import org.benedetto.ifr.adjancencylist.FeedBack;
import org.benedetto.ifr.flickr.FlickrCache;
import org.benedetto.ifr.flickr.InvalidCacheRequestException;
import org.benedetto.ifr.util.ComparablePair;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 12/26/13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
public class TopologicalSortTest {

    @Test
    public void test() {
        AcyclicWeightedGraph<String> graph = new AcyclicWeightedGraph<>();
        FeedBack first = new FeedBack("c", Arrays.asList(new String [] { "a", "b", "c" }));
        graph.addFeedBack(first);
        FeedBack second = new FeedBack("b", Arrays.asList(new String [] { "e", "b", "d" }));
        graph.addFeedBack(second);
        FeedBack third = new FeedBack("5", Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7"}));
        graph.addFeedBack(third);
        ArrayList<String> nodes = new ArrayList<>(graph.nodes());
        new ClosureGraph<String>(graph).sort(nodes);
        System.err.println(nodes);
        assertTrue(nodes.indexOf("a") > nodes.indexOf("c"));
        assertTrue(nodes.indexOf("b") > nodes.indexOf("c"));
        assertTrue(nodes.indexOf("e") > nodes.indexOf("b"));
        assertTrue(nodes.indexOf("d") > nodes.indexOf("b"));
        assertTrue(nodes.indexOf("1") > nodes.indexOf("5"));
        assertTrue(nodes.indexOf("2") > nodes.indexOf("5"));
        assertTrue(nodes.indexOf("3") > nodes.indexOf("5"));
        assertTrue(nodes.indexOf("4") > nodes.indexOf("5"));
        assertTrue(nodes.indexOf("6") > nodes.indexOf("5"));
        assertTrue(nodes.indexOf("7") > nodes.indexOf("5"));
    }

    @Test
    public void testWithFlickr() throws IOException, InterruptedException, InvalidCacheRequestException {
        FlickrCache cache = new FlickrCache();
        List<String> imageUrls = cache.getImageUrls(20, "Ferienhaus");
        String selected = imageUrls.get(1);
        FeedBack<String> feedBack = new FeedBack<>(selected, imageUrls);
        System.err.println(new Gson().toJson(feedBack));
        AcyclicWeightedGraph<String> graph = new AcyclicWeightedGraph<>();
        graph.addFeedBack(feedBack);
        List<String> allImages = cache.getImageUrls(FlickrCache.querySize, "Ferienhaus");
        ClosureGraph<String> closureGraph = new ClosureGraph<>(graph);
        closureGraph.sort(allImages);
        System.err.println(allImages);
        int indexOfSelected = allImages.indexOf(selected);
        for (String displayed : imageUrls) {
            System.err.println(allImages.indexOf(displayed) + "; " + indexOfSelected);
            assertTrue(allImages.indexOf(displayed) >= indexOfSelected);
        }
    }

    @Test
    public void testCustomSort() {
        @SuppressWarnings("unchecked")
        ClosureGraph<String> closureGraph = new ClosureGraph<>();
        closureGraph.add(new ComparablePair<>("a", "b"));
        closureGraph.add(new ComparablePair<>("j", "f"));
        List<String> allUrls = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "j");
        closureGraph.sort(allUrls);
        for (ComparablePair<String,String> pair: closureGraph) {
            assertTrue(allUrls.indexOf(pair.second) < allUrls.indexOf(pair.first));
        }
    }


    @Test
    public void testRanking() {
        AcyclicWeightedGraph<String> graph = new AcyclicWeightedGraph<>();
        graph.addEdge("a", "b", 1.0f);
        graph.addEdge("j", "f", 1.0f);
        HashMap<String, Float> ranking = graph.ranking();
        assertTrue(ranking.get("a") < ranking.get("b"));
        assertTrue(ranking.get("j") < ranking.get("f"));
    }

}
