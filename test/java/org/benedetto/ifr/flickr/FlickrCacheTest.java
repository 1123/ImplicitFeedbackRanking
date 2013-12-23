package org.benedetto.ifr.flickr;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class FlickrCacheTest {

    @Test
    public void testCache() throws IOException {
        FlickrCache cache = new FlickrCache();
        List<PhotoDetails> result = cache.search("katze");
        System.err.println(result.size());
        result = cache.search("katze");
        System.err.println(result.size());
        result = cache.search("hund");
        System.err.println(result.size());
    }

    @Test
    public void testMultipleTags() throws IOException, InvalidCacheRequestException {
        FlickrCache cache = new FlickrCache();
        Collection<String> urls = cache.getImageUrls(10, "Ferienwohnung Bayern", ImageSize.m);
        System.out.println(urls);
    }

    @Test
    public void testGetUrls() throws IOException, InvalidCacheRequestException {
        FlickrCache cache = new FlickrCache();
        Collection<String> urls = cache.getImageUrls(10, "Ferienhaus", ImageSize.m);
        System.out.println(urls);
    }

    @Test
    public void testRandomSelection() throws IOException, InvalidCacheRequestException {
        // TODO: test that the result does not contain any duplicates
        FlickrCache cache = new FlickrCache();
        List<PhotoDetails> randomSelection = cache.search("katze", 10);
        System.err.println(new Gson().toJson(randomSelection));
        randomSelection = cache.search("katze", 10);
        System.err.println(new Gson().toJson(randomSelection));
    }

}

