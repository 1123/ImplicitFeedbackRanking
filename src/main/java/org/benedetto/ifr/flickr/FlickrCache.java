package org.benedetto.ifr.flickr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// This class caches the search results from flickr.
// We may use the httpclient for this, yet this would cache at a lower level (which is also fine)

public class FlickrCache {

    static final int querySize = 100;

    HashMap<String, List<PhotoDetails>> cache;

    public FlickrCache() {
        this.cache = new HashMap<>();
    }

    public List<PhotoDetails> search(String tags, int number) throws InvalidCacheRequestException, IOException {
        if (number > querySize) throw new InvalidCacheRequestException();
        List<PhotoDetails> searchResults = this.search(tags);
        if (number >= searchResults.size()) {
            return searchResults;
        }
        List<PhotoDetails> result = new ArrayList<>();
        Random r = new Random();
        while (result.size() < number) {
            int position = r.nextInt(searchResults.size());
            if (result.contains(this.search(tags).get(position))) continue;
            result.add(this.search(tags).get(position));
        }
        return result;
    }

    public List<PhotoDetails> search(String tags) throws IOException {
        if (this.cache.containsKey(tags)) {
            return this.cache.get(tags);
        }
        FlickrRestClient client = new FlickrRestClient();
        List<PhotoDetails> results = client.getImages(querySize, tags);
        this.cache.put(tags, results);
        return results;
    }

    public List<String> getImageUrls(int number, String tags) throws IOException, InvalidCacheRequestException {
        List<PhotoDetails> photos = this.search(tags, number);
        List<String> result = new ArrayList<>();
        for (PhotoDetails photoDetails : photos) {
            result.add(photoDetails.getUrl());
        }
        return result;
    }


}
