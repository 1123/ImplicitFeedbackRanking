package org.benedetto.ifr.flickr;

import com.google.gson.Gson;
import org.benedetto.ifr.util.HttpClientWrapper;

import java.io.IOException;
import java.util.List;

public class FlickrRestClient {
    static String apiKeyParam = "api_key=89d44bcfe53f4000e716498741a61185";
    static String restService = "http://api.flickr.com/services/rest/";

    public static String search(int perPage, String tags) throws IOException {
        // tags are separated by spaces. These must be URL encoded.
        String escapedTags = tags.replaceAll(" ", "%20");
        String url = String.format(
                "%s?method=flickr.photos.search&per_page=%d&nojsoncallback=1&format=json&tags=%s&%s",
                restService, perPage, escapedTags, apiKeyParam);
        return HttpClientWrapper.get(url);
    }

    public static List<PhotoDetails> getImages(int number, String tags) throws IOException {
        String json = search(number, tags);
        FlickrSearchResponse response = new Gson().fromJson(json, FlickrSearchResponse.class);
        return response.photos.photo;
    }

}
