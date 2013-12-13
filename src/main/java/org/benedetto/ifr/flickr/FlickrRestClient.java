package org.benedetto.ifr.flickr;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlickrRestClient {
    static String apiKeyParam = "api_key=89d44bcfe53f4000e716498741a61185";
    static String restService = "http://api.flickr.com/services/rest/";
    static String searchUrl = restService +
            "?method=flickr.photos.search&per_page=1" +
            "&nojsoncallback=1&format=json&tags=ferienwohnung&" +
            apiKeyParam;
    static String echoUrl = restService + "?method=flickr.test.echo&name=value&" + apiKeyParam;

    HttpClient client;

    public FlickrRestClient() {
        client = new HttpClient();
    }

    public String search(int perPage, String tags) throws IOException {
        // tags are separated by spaces. These must be URL encoded.
        String escapedTags = tags.replaceAll(" ", "%20");
        String url = String.format(
                "%s?method=flickr.photos.search&per_page=%d&nojsoncallback=1&format=json&tags=%s&%s",
                restService, perPage, escapedTags, apiKeyParam);
        return this.get(url);
    }

    public String get(String url) throws IOException {
        System.err.println(url);
        HttpMethod method = new GetMethod(url);
        client.executeMethod(method);
        byte[] responseBody = method.getResponseBody();
        method.releaseConnection();
        return new String(responseBody);
    }

    public List<PhotoDetails> getImages(int number, String tags) throws IOException {
        String json = this.search(number, tags);
        FlickrSearchResponse response = new Gson().fromJson(json, FlickrSearchResponse.class);
        return response.photos.photo;
    }

    public List<String> getImageUrls(int number, String tags) throws IOException {
        List<PhotoDetails> photos = this.getImages(number, tags);
        List<String> result = new ArrayList<>();
        for (PhotoDetails photoDetails : photos) {
            result.add(photoDetails.getUrl());
        }
        return result;
    }
}
