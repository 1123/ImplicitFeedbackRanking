package org.benedetto.ifr.flickr;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;

public class FlickrTest {

    @Test
    public void testMultipleTags() throws IOException {
        FlickrRestClient flickrRestClient = new FlickrRestClient();
        List<String> urls = flickrRestClient.getImageUrls(10, "Ferienwohnung Bayern");
        System.out.println(urls);
    }

    @Test
    public void testFerienwohnung() throws IOException {
        FlickrRestClient flickrRestClient = new FlickrRestClient();
        List<PhotoDetails> details = flickrRestClient.getImages(10, "Ferienwohnung");
        System.out.println(details.size());
    }

    @Test
    public void testGetUrls() throws IOException {
        FlickrRestClient flickrRestClient = new FlickrRestClient();
        List<String> urls = flickrRestClient.getImageUrls(10, "Ferienhaus");
        System.out.println(urls);
    }

    @Test
    public void testSearch() throws IOException {
        FlickrRestClient flickrRestClient = new FlickrRestClient();
        String json = flickrRestClient.get(FlickrRestClient.searchUrl);
        System.out.println(json);
        FlickrSearchResponse response = new Gson().fromJson(json, FlickrSearchResponse.class);
        System.err.println(new Gson().toJson(response));
    }

}

